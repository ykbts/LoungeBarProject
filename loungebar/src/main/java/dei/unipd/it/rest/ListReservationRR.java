package dei.unipd.it.rest;

import dei.unipd.it.resources.Actions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

/**
 * A REST resource for listing {@link dei.unipd.it.resources.Reservation}s.
 */
public final class ListReservationRR extends AbstractRR{

    /**
     * Creates a new REST resource for listing reservations.
     *
     * @param req the HTTP request.
     * @param resp the HTTP response.
     * @param conn the connection to the database.
     */
    public ListReservationRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.LIST_RESERVATIONS, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

    }
}
