package dei.unipd.it.rest;

import dei.unipd.it.database.CreateReservationDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.Reservation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A REST resource for creating a new {@link Reservation}.
 */
public final class CreateReservationRR extends AbstractRR {

    /**
     * Creates a new REST resource for creating a {@code Reservation}.
     *
     * @param req the HTTP request.
     * @param resp the HTTP response.
     * @param conn the connection to the database.
     */
    public CreateReservationRR(final HttpServletRequest req, final HttpServletResponse resp, Connection conn) {
        // the constructor set the parameters only
        super(Actions.CREATE_RESERVATION, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        Reservation reservation;
        Message message;

        try {
            // reads the JSON object and create the resource
            Reservation r = Reservation.fromJSON(req.getInputStream());

            // set Logger's resource
            LogContext.setResource(Integer.toString(r.getId()));

            /// create a new DAO for accessing the database and store the Reservation
            reservation = new CreateReservationDAO(conn, r).access().getOutputParam();

            if (reservation != null) {

                LOGGER.info("Reservation successfully created");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                /// make the resource write itself
                reservation.toJSON(resp.getOutputStream());
            }
            else {
                LOGGER.error("Fatal error while creating RESERVATION.");

                message = new Message("Cannot create the reservation: unexpected error.", "E5A1", null);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }

        }
        catch (EOFException e) {
            LOGGER.warn("Cannot create RESERVATION: no Reservation JSON object found in the request.", e);

            message = new Message("Cannot create RESERVATION: no Reservation JSON object found in the request.", "E4A8", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message.toJSON(resp.getOutputStream());
        }
        catch (SQLException ex) {
        String sqlState = ex.getSQLState();
        String msg = ex.getMessage();

        String friendlyMessage;
        int statusCode = HttpServletResponse.SC_BAD_REQUEST;

        if ("23514".equals(sqlState)) {
            if (msg.contains("reservation_duration_check")) {
                friendlyMessage = "Reservation must be between 1 and 4 hours";
            } else if (msg.contains("n_people")) {
                friendlyMessage = "Number of people must be greater than 0";
            } else {
                friendlyMessage = "Invalid reservation data (constraint violation)";
            }
        }
        else if ("23503".equals(sqlState)) {
            if (msg.contains("user_customerID")) {
                friendlyMessage = "Customer does not exist";
            } else if (msg.contains("tableNumber")) {
                friendlyMessage = "Selected table does not exist";
            } else {
                friendlyMessage = "Referenced data not found";
            }
        }
        else if ("23505".equals(sqlState)) {
            friendlyMessage = "This reservation already exists";
            statusCode = HttpServletResponse.SC_CONFLICT;
        }
        else if (msg.contains("already reserved")) {
            friendlyMessage = "This table is already reserved for the selected time";
        }
        else if (msg.contains("capacity exceeded")) {
            friendlyMessage = "Too many people for this table";
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