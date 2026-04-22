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

public final class CreateReservationRR extends AbstractRR {

    public CreateReservationRR(final HttpServletRequest req, final HttpServletResponse resp, Connection conn) {
        // the constructor set the parameters only
        super(Actions.CREATE_RESERVATION, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        Reservation reservation = null;
        Message message;

        try {
            // reads the JSON object and create the resource
            Reservation r = Reservation.fromJSON(req.getInputStream());

            // set Logger's resource
            LogContext.setResource(Long.toString(r.getId()));

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

            if ("23505".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot create the RESERVATION: it already exists.");

                message = new Message("Cannot create the reservation: it already exists.", "E5A2", ex.getMessage());
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                message.toJSON(resp.getOutputStream());
            }
            else {
                LOGGER.error("Cannot create the RESERVATION: unexpected database error.", ex);

                message = new Message("Cannot create the reservation: unexpected database error.", "E5A1", ex.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }
        }

    }
}
