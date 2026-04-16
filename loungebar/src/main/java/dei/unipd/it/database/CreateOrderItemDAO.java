package dei.unipd.it.database;
import dei.unipd.it.resources.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateOrderItemDAO extends AbstractDAO<OrderItem> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.OrderItem (drink_name, orderID, quantity) VALUES (?, ?, ?) RETURNING *";
    private final OrderItem orderItem;

    public CreateOrderItemDAO (final Connection connection, final OrderItem orderItem) {
        super(connection);

        if (orderItem == null) {
            LOGGER.error("The order item cannot be null.");
            throw new NullPointerException("The order item cannot be null.");
        }
        this.orderItem = orderItem;
    }

    /** prepare and execute the query */
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        OrderItem oi = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, orderItem.getDrinkName());
            preparedStatement.setLong(2, orderItem.getOrderID());
            preparedStatement.setInt(3, orderItem.getQuantity());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                oi = new OrderItem(rs.getString("drink_name"),
                        rs.getLong("orderID"),
                        rs.getInt("quantity"));

                LOGGER.info("Order {} successfully stored.", oi.getDrinkName());
            }
        }

        outputParam = oi;
    }
}


