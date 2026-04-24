package dei.unipd.it.database;

import dei.unipd.it.resources.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for adding an item to an order.
 *
 * <p>Saves a drink, order ID, and quantity in the database.</p>
 *
 * <p>Returns the created {@link OrderItem}.</p>
 */
public class CreateOrderItemDAO extends AbstractDAO<OrderItem> {

    /** * SQL statement used to insert a new order item and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.OrderItem (drink_name, orderID, quantity) VALUES (?, ?, ?) RETURNING *";

    /**
     * The order item data to be persisted.
     */
    private final OrderItem orderItem;

    /**
     * Creates a new DAO for inserting a specific order item.
     *
     * @param connection the database connection
     * @param orderItem the item object containing the data to be stored
     * @throws NullPointerException if {@code orderItem} is null
     */
    public CreateOrderItemDAO (final Connection connection, final OrderItem orderItem) {
        super(connection);

        if (orderItem == null) {
            LOGGER.error("The order item cannot be null.");
            throw new NullPointerException("The order item cannot be null.");
        }
        this.orderItem = orderItem;
    }

    /**
     * Creates an order item in the database.
     *
     * <p>Sets item data, executes the insert, and returns the saved {@link OrderItem}.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
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
                        rs.getInt("orderID"),
                        rs.getInt("quantity"));

                LOGGER.info("Order item {} successfully stored for order {}.", oi.getDrinkName(), oi.getOrderID());
            }
        }

        outputParam = oi;
    }
}