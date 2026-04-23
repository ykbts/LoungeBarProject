package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchOrderDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.Orders;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet(name = "SearchOrderServlet", value = "/order/search")
public final class SearchOrderServlet extends AbstractDatabaseServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());  
        LogContext.setAction(Actions.SEARCH_ORDER);
        Message m= null;
        List<Orders> ol = null;
        try {
        // Get id of the waiter from the session
        HttpSession session = req.getSession(false);
        String waiterID = (session != null) ? (String) session.getAttribute("user") : null;

        if (waiterID == null) {
            m = new Message("You must be logged in to see your orders.", "E401", "");
        } else {
            // Call DAO with the waiter ID to get the orders of that waiter
            ol = new SearchOrderDAO(getConnection(), -1, null, null, null, waiterID, -1)
                    .access().getOutputParam();

            m = new Message("Orders successfully retrieved.");
        }

    } catch (SQLException ex) {
        m = new Message("Error accessing the database", "E200", ex.getMessage());
        LOGGER.error("Error searching orders", ex);
    }

    // Send data to jsp
    req.setAttribute("orderList", ol);
    req.setAttribute("message", m);
    req.getRequestDispatcher("/jsp/protected/search-order-result.jsp").forward(req, res);
}
}