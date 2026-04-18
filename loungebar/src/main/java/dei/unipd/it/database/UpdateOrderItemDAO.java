package dei.unipd.it.database;

import dei.unipd.it.resources.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UpdateOrderItemDAO extends AbstractDAO<OrderItem> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE lounge.OrderItem SET quantity = ? WHERE orderID = ? AND drink_name = ?;";

    private final OrderItem orderItem;

    public UpdateOrderItemDAO(final Connection con, final OrderItem orderItem) {
    super(con);

        if (orderItem == null) {
            LOGGER.error("The order item cannot be null");
            throw new NullPointerException("The order item cannot be null");
        }

        if (orderItem.getOrderID() <= 0) {
            LOGGER.error("Invalid order ID provided for update");
            throw new IllegalArgumentException("Invalid order ID");
        }

        if (orderItem.getDrinkName() == null || orderItem.getDrinkName().isBlank()) {
            LOGGER.error("Invalid drink name provided for update");
            throw new IllegalArgumentException("Invalid drink name");
        }

        this.orderItem = orderItem;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);

            pstmt.setInt(1, orderItem.getQuantity());
            pstmt.setLong(2, orderItem.getOrderID());
            pstmt.setString(3, orderItem.getDrinkName());

            pstmt.execute();

            LOGGER.info("Order item {} for order {} successfully updated.", orderItem.getDrinkName(), orderItem.getOrderID());
            this.outputParam = orderItem;


        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}