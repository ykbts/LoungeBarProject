package dei.unipd.it.rest;

import dei.unipd.it.database.CreateReviewDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class CreateReviewRR extends AbstractRR {

    public CreateReviewRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.CREATE_REVIEW, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        Review review;
        Message message;

        try {
            Review r = Review.fromJSON(req.getInputStream());
            LogContext.setResource(r.getCustomerID() + ", " + r.getWaiterID());

            review = new CreateReviewDAO(conn, r).access().getOutputParam();

            if (review != null) {

                LOGGER.info("Reservation successfully created");
                resp.setStatus(HttpServletResponse.SC_CREATED);
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
            if ("23505".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot create the REVIEW: it already exists.");

                message = new Message("Cannot create the REVIEW: it already exists.", "E5A2", ex.getMessage());
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                message.toJSON(resp.getOutputStream());
            }
            else {
                LOGGER.error("Cannot create the REVIEW: unexpected database error.", ex);

                message = new Message("Cannot create the REVIEW: unexpected database error.", "E5A1", ex.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }
        }

    }
}
