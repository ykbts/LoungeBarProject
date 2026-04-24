package dei.unipd.it.rest;

import dei.unipd.it.database.CreateReviewDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A REST resource for creating a new {@link Review}.
 */
public final class CreateReviewRR extends AbstractRR {

    /**
     * Creates a new REST resource for creating a {@code Review}.
     *
     * @param req the HTTP request.
     * @param resp the HTTP response.
     * @param conn the connection to the database.
     */
    public CreateReviewRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.CREATE_REVIEW, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        Review review;
        Message message;

        try {
            HttpSession session = req.getSession(false);
            String clientId = (session != null) ? (String) session.getAttribute("user") : null;

            if (clientId == null) {
                message = new Message("Session expired. Please login again.", "E401", "User not found in session.");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                message.toJSON(resp.getOutputStream());
                return;
            }
            // reads the JSON object and create the resource
            Review r = Review.fromJSON(req.getInputStream());
            r.setCustomer(clientId);
            // set Logger's resource
            LogContext.setResource(r.getCustomerID() + ", " + r.getWaiterID());

            // create a new DAO for accessing the database and store the Review
            review = new CreateReviewDAO(conn, r).access().getOutputParam();

            if (review != null) {

                LOGGER.info("Review successfully created");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                // make the resource write itself
                review.toJSON(resp.getOutputStream());
            }
            else {
                LOGGER.error("Fatal error while creating REVIEW.");

                message = new Message("Cannot create the REVIEW: unexpected error.", "E5A1", null);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }
        }
        catch (EOFException e) {
            LOGGER.warn("Cannot create REVIEW: no Review JSON object found in the request.", e);

            message = new Message("Cannot create REVIEW: no Review JSON object found in the request.", "E4A8", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message.toJSON(resp.getOutputStream());
        }
        catch (SQLException ex) {
            String sqlState = ex.getSQLState();
            String msg = ex.getMessage();

            String friendlyMessage;
            int statusCode = HttpServletResponse.SC_BAD_REQUEST;

            if ("23505".equals(sqlState)) {
                friendlyMessage = "You have already submitted a review for this waiter";
                statusCode = HttpServletResponse.SC_CONFLICT;
            }
            else if ("23503".equals(sqlState)) {
                if (msg.contains("user_waiterID")) {
                    friendlyMessage = "Selected waiter does not exist";
                } else if (msg.contains("user_customerID")) {
                    friendlyMessage = "Customer does not exist";
                } else {
                    friendlyMessage = "Referenced user not found";
                }
            }
            else if ("23514".equals(sqlState)) {
                friendlyMessage = "Score must be between 1 and 5";
            }
            else {
                friendlyMessage = "Unexpected database error";
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }

            LOGGER.error("SQL Error: %s - %s", sqlState, msg);
            try {
                resp.reset();
                resp.setContentType("application/json; charset=utf-8");
                resp.setStatus(statusCode);
                message = new Message(friendlyMessage, "E5A1", msg);
                message.toJSON(resp.getOutputStream());
            } catch (Exception ioe) {
                LOGGER.error("Failed to write error response: %s", ioe.getMessage(), ioe);
            }
        }
    }
}