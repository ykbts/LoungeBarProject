package dei.unipd.it.rest;

import dei.unipd.it.database.SearchUserDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * A REST resource for listing {@link dei.unipd.it.resources.User}s with the role of waiter.
 */
public final class ListWaitersRR extends AbstractRR {

    /**
     * Creates a new REST resource for listing waiters.
     *
     * @param req the HTTP request.
     * @param resp the HTTP response.
     * @param conn the connection to the database.
     */
    public ListWaitersRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.LIST_REVIEW, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {
        Message message;

        try {
            List<User> waiters = new SearchUserDAO(conn, null, null, null, null, null, "WAITER")
                    .access().getOutputParam();

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json; charset=utf-8");
            var out = resp.getOutputStream();
            out.write("[".getBytes());
            for (int i = 0; i < waiters.size(); i++) {
                String json = "\"" + waiters.get(i).getUsername() + "\"";
                out.write(json.getBytes());
                if (i < waiters.size() - 1) out.write(",".getBytes());
            }
            out.write("]".getBytes());

        } catch (SQLException ex) {
            message = new Message("Cannot list waiters.", "E5A1", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(resp.getOutputStream());
        }
    }
}