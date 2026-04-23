package dei.unipd.it.rest;

import dei.unipd.it.database.UpdateOrderStatusDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.OrderState;
import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.PayMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class UpdateOrderStatusRR extends AbstractRR {
    public UpdateOrderStatusRR(final HttpServletRequest req, final HttpServletResponse resp, Connection conn) {
        // Use action of update order status
        super(Actions.UPDATE_ORDER_STATUS, req, resp, conn);
    }
    @Override
    protected void doServe() throws IOException {
        Message message;
        Orders updatedOrder;

        try {
            // Extract parameters
            int orderID = Integer.parseInt(req.getParameter("orderID"));
            OrderState newState = OrderState.valueOf(req.getParameter("newStatus"));
            PayMethod newPayMethod = PayMethod.valueOf(req.getParameter("payMethod"));

            LogContext.setResource(Integer.toString(orderID));

            // Execute DAO
            updatedOrder = new UpdateOrderStatusDAO(conn, orderID, newState, newPayMethod).access().getOutputParam();

            if (updatedOrder != null) {
                LOGGER.info("Order %d status updated successfully", orderID);
                resp.setStatus(HttpServletResponse.SC_OK);
                // 
                updatedOrder.toJSON(resp.getOutputStream());
            } else {
                LOGGER.error("Order not found: %d", orderID);
                message = new Message("Cannot update: order not found.", "E404", null);
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                message.toJSON(resp.getOutputStream());
            }

        } catch (NullPointerException | IllegalArgumentException e) {
            LOGGER.warn("Invalid input for update status.", e);
            message = new Message("Invalid input parameters.", "E400", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message.toJSON(resp.getOutputStream());
        } catch (SQLException ex) {
            LOGGER.error("Database error while updating order status.", ex);
            message = new Message("Database error.", "E500", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(resp.getOutputStream());
        }
    }
}
