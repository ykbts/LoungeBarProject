package dei.unipd.it.rest;

import dei.unipd.it.database.CreateOrderItemDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.OrderItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddItemsRR extends AbstractRR {


    /**
     * Creates a new OrderItems Rest Resource.
     *
     * @param req    the HTTP request.
     * @param resp   the HTTP response.
     * @param conn   the connection to the database.
     */
    public AddItemsRR(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.ADD_ITEMS, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

        OrderItem item;
        Message message;

        try {
            OrderItem oi = OrderItem.fromJSON(req.getInputStream());

            item = new CreateOrderItemDAO(conn, oi).access().getOutputParam();

            if (item != null) {

                LOGGER.info("ORDERITEM successfully created");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                item.toJSON(resp.getOutputStream());

            }
            else {
                LOGGER.error("Fatal error while creating ORDERITEM.");

                message = new Message("Cannot create the ORDERITEM: unexpected error.", "E5A1", null);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(resp.getOutputStream());
            }

        }
        catch (EOFException e) {
            LOGGER.warn("Cannot create ORDERITEM: no OrderItem JSON object found in the request.", e);

            message = new Message("Cannot create ORDERITEM: no OrderItem JSON object found in the request.", "E4A8", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message.toJSON(resp.getOutputStream());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
