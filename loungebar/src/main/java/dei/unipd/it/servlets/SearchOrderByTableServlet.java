package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchOrderDAO;
import dei.unipd.it.database.SearchOrderItemsByTableDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.OrderItem;
import dei.unipd.it.resources.Orders;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchOrderByTableServlet", value = "/order/myOrder")
public final class SearchOrderByTableServlet extends AbstractDatabaseServlet {

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
        Orders order = null;
        Message m = null;

        try {
            int table = Integer.parseInt(req.getParameter("table"));

            // search for ORDERITEMLIST
            orderItemList = new SearchOrderItemsByTableDAO(getConnection(), table).access().getOutputParam();
            LOGGER.info("Order's ITEMS successfully searched for table: {}", table);

            if (!orderItemList.isEmpty()) {
                // search for ORDER
                order = new SearchOrderDAO(getConnection(), -1, null, "PENDING", null, null, table).access().getOutputParam().get(0);
                LOGGER.info("PENDING Order ({}) successfully searched for table: {}", order.getId() ,table);
            }

            m = new Message("Order successfully searched");

        }
        catch (SQLException ex) {

            m = new Message("Unexpected error while accessing the database", "E200",
                    ex.getMessage());

            LOGGER.error("Unexpected error while accessing the database searching for ORDER", ex);
        }

        try {
            req.setAttribute("orderID", order != null ? order.getId() : -1);
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
