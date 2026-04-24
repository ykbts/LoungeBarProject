package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchOrderItemsByTableDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchOrderItemByTableServlet", value = "/order/myOrder")
public class SearchOrderItemByTableServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setResource(req.getRequestURI());

        req.getRequestDispatcher("/jsp/protected/search-orderitems-table.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LogContext.setAction(Actions.SEARCH_MY_ORDER);

        List<OrderItem> orderItemList = null;
        Message m = null;

        try {
            int table = Integer.parseInt(req.getParameter("table"));

            orderItemList = new SearchOrderItemsByTableDAO(getConnection(), table).access().getOutputParam();

            LOGGER.info("Items successfully searched for table: {}", table);
            m = new Message("Items successfully searched");

        }
        catch (SQLException ex) {

            m = new Message("Unexpected error while accessing the database", "E200",
                    ex.getMessage());

            LOGGER.error("Unexpected error while accessing the database searching for ORDER_ITEMS", ex);
        }

        try {
            req.setAttribute("orderItemList", orderItemList);
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/protected/search-orderitems-table-result.jsp").forward(req, resp);
        }
        catch (Exception e) {
            LOGGER.error("Unable to send response when searching ORDER_ITEMS.", e);
        }
        finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }
}
