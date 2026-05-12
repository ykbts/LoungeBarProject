package dei.unipd.it.rest;

import dei.unipd.it.database.SearchTableDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.ResourceList;
import dei.unipd.it.resources.Tables;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListTablesRR extends AbstractRR {
    /**
     * Creates a new REST resource.
     *
     * @param req    the HTTP request.
     * @param resp   the HTTP response.
     * @param conn   the connection to the database.
     */
    public ListTablesRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.LIST_TABLES, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        List<Tables> tables;
        Message message = null;

        try {
            // call the dao and populate the list
            tables = new SearchTableDAO(conn, -1, null, null).access().getOutputParam();

            if (tables != null) {

                LOGGER.info("TABLES successfully listed.");
                resp. setStatus(HttpServletResponse.SC_OK);
                // create the JSON object
                new ResourceList<>(tables).toJSON(resp.getOutputStream());
            }
            else {
                LOGGER.error("Fatal error while listing TABLES.");

                message = new Message("Cannot list TABLES: unexpected error.", "E5A1", null);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }

        } catch (SQLException e) {
            LOGGER.error("Cannot list TABLES: unexpected database error.", e);

            message = new Message("Cannot list TABLES: unexpected database error.", "E5A1", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(resp.getOutputStream());
        }
    }
}
