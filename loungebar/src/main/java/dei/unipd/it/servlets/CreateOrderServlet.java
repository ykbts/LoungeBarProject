package dei.unipd.it.servlets;

import dei.unipd.it.database.CreateOrderDAO;
import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.PayMethod;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.OrderState;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.StringFormattedMessage;


@WebServlet(name = "CreateOrderServlet", value = "/order/*")
public final class CreateOrderServlet extends AbstractDatabaseServlet {

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

            // Create object
            o = new Orders(-1, dateTime, status, payMethod, waiterID, tableID);
            
            
            o = new CreateOrderDAO(getConnection(), o).access().getOutputParam();

            m = new Message(String.format("Order #%d created for table %d.", o.getId(), tableID));
            LOGGER.info("Order successfully created for user {}", waiterID);

        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m = new Message("Invalid Operation", "E404", "The resource is not available.");
        }

    } catch (Exception ex) {
        // Capture everything
        m = new Message("Cannot create order", "E500", ex.getMessage());
        LOGGER.error("Order creation failed", ex);
    }

    try {
            //  DISPATCHER
            req.setAttribute("order", o);
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/create-order-result.jsp").forward(req, res);

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
        