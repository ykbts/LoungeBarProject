package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchShiftByWaiterDAO;
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

@WebServlet(name = "SearchWaiterShiftServlet", value = "/shift/search-by-waiter")
public final class SearchWaiterShiftServlet extends AbstractDatabaseServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction("SEARCH_WAITER_SHIFT");

        HttpSession session = req.getSession(false);
        String waiterID = (String) session.getAttribute("user");

        List<Shift> sl = null;
        Message m = null;

        try {
            sl = new SearchShiftByWaiterDAO(getConnection(), waiterID)
                    .access().getOutputParam();
            m = new Message("Shifts successfully searched");
            LOGGER.info("Shifts successfully searched for waiter: %s", waiterID);

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