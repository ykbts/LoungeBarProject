package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchDrinkDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Drink;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
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
        Message m = null;

        try {

            // create the menù
            menu = new SearchDrinkDAO(getConnection(), null, 0, null).access().getOutputParam();

            LOGGER.info("Drinks successfully listed.");
        }
        catch (SQLException ex) {

            LOGGER.error("Drink listing failed", ex);
            m = new Message("Cannot list the menù", "E500", ex.getMessage());
        }

        try {
            req.setAttribute("menu", menu);
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
