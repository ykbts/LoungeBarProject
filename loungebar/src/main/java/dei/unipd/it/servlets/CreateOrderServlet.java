package dei.unipd.it.servlets;

import dei.unipd.it.database.CreateOrderDAO;
import dei.unipd.it.database.SearchDrinkDAO;
import dei.unipd.it.resources.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet for creating a new order.
 * * <p>It reads order data from a web form, saves it to the database
 * using a DAO, and shows the result on a JSP page.</p>
 */
@WebServlet(name = "CreateOrderServlet", value = "/order/*")
public final class CreateOrderServlet extends AbstractDatabaseServlet {

    /**
     * Handles the creation of a new order via POST.
     * @param req the request from the user.
     * @param res the response to the user.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an input or output error occurs.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        
        String pathInfo = req.getPathInfo();
        String op = (pathInfo != null) ? pathInfo.replace("/", "") : "";     

        Orders o = null;
        Message m = null;

    try {
        if ("create".equals(op)) {
            // Validate logged user
            LogContext.setAction(Actions.CREATE_ORDER);
            HttpSession session = req.getSession(false);
            String waiterID = (session != null) ? (String) session.getAttribute("user") : null;
            
            if (waiterID == null) {
                m = new Message("Session expired. Please login again.", "E401", "User not found in session.");
            }
            // Get data
            int tableID = Integer.parseInt(req.getParameter("tableID"));
            String dateParam = req.getParameter("dateTime");
            LocalDateTime dateTime = LocalDateTime.parse(dateParam.replace(" ", "T"));
            OrderState status = OrderState.valueOf(req.getParameter("status"));

            PayMethod payMethod = null; 

            LogContext.setResource("Table_" + tableID);

            // Create order
            o = new Orders(-1, dateTime, status, payMethod, waiterID, tableID);


            o = new CreateOrderDAO(getConnection(), o).access().getOutputParam();

            m = new Message(String.format("Order #%d created for table %d.", o.getId(), tableID));
            LOGGER.info("Order successfully created for user {}", waiterID);

            List<Drink> menu = new SearchDrinkDAO(getConnection(), null, 0, null).access().getOutputParam();
            req.setAttribute("menu", menu);


        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m = new Message("Invalid Operation", "E404", "The resource is not available.");
        }

    } catch (Exception ex) {
        // Capture everything
        LOGGER.error("Order creation failed", ex);

        o = null;
        m = new Message("Cannot create order", "E500", ex.getMessage());
//        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        res.setContentType(JSON_UTF_8_MEDIA_TYPE);
//        m.toJSON(res.getOutputStream());
    }

    try {
            //  DISPATCHER
            req.setAttribute("order", o);
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/protected/create-order-result.jsp").forward(req, res);

        } catch (Exception ex) {
            LOGGER.error("Unable to send response for order creation.", ex);
            throw ex;
        } finally {
            // 
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    } 

} 
        