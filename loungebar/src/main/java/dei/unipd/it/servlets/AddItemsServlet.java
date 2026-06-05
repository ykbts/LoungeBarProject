package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchDrinkDAO;
import dei.unipd.it.database.SearchOrderItemDAO;
import dei.unipd.it.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AddItemsServlet", value = "/order/add-items")
public final class AddItemsServlet extends AbstractDatabaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setResource(req.getRequestURI());
        LogContext.setAction(Actions.LIST_DRINKS);

        List<Drink> menu = null;
        List<OrderItem> order = null;
        Message m = null;
        int id = -1;

        try {

            // create the menù
            menu = new SearchDrinkDAO(getConnection(), null, 0, null).access().getOutputParam();
            LOGGER.info("Drinks successfully listed.");

            // list all the client order items
            id = Integer.parseInt(req.getParameter("orderID"));
            order = new SearchOrderItemDAO(getConnection(), null, id, -1).access().getOutputParam();
            LOGGER.info("Order successfully listed.");

            m = new Message("Drinks and Order successfully listed.");
        }
        catch (SQLException ex) {

            LOGGER.error("Drink listing failed", ex);
            m = new Message("Cannot list the menù", "E500", ex.getMessage());
        }

        try {
            req.setAttribute("orderID", id);
            req.setAttribute("menu", menu);
            req.setAttribute("order", order);
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/protected/add-items.jsp").forward(req, resp);
        }
        finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }

    }

}
