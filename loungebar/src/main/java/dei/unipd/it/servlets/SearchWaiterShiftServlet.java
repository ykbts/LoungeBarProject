package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchShiftByWaiterDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Shift;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.StringFormattedMessage;

/**
 * Servlet for searching shifts assigned to a specific waiter
 */
@WebServlet(name = "SearchWaiterShiftServlet", value = "/shift/search-by-waiter")
public final class SearchWaiterShiftServlet extends AbstractDatabaseServlet {

    /**
     * Handles the search request for waiter shifts via POST.
     *
     * @param req the request from the user.
     * @param res the response to the user.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an input or output error occurs.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.SEARCH_WAITER_SHIFT);

        HttpSession session = req.getSession(false);
        String waiterID = (String) session.getAttribute("user");

        List<Shift> sl = null;
        Message m = null;

        try {
            sl = new SearchShiftByWaiterDAO(getConnection(), waiterID)
                    .access().getOutputParam();
            m = new Message("Shifts successfully searched");
            LOGGER.info("Shifts successfully searched for waiter: {}", waiterID);

        } catch (SQLException ex) {
            m = new Message("Cannot search for shifts: unexpected error while accessing the database",
                    "E200", ex.getMessage());
            LOGGER.error("Cannot search for shifts.", ex);
        }

        try {
            req.setAttribute("shiftList", sl);
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/protected/search-waiter-shift-result.jsp").forward(req, res);
        } catch (Exception ex) {
            LOGGER.error("Unable to send response when searching waiter shifts.", ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
        }
    }
}