package dei.unipd.it.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for deleting a specific item from an order.
 *
 * <p>This class removes a single {@code OrderItem} identified by its
 * {@code drink_name} and {@code orderID}. It performs input validation
 * and returns a boolean indicating whether the deletion was successful.</p>
 *
 * <p>The operation does not return the deleted object, but instead
 * returns {@code true} if at least one row was affected, and {@code false}
 * otherwise.</p>
 */
public final class DeleteOrderItemDAO extends AbstractDAO<Boolean> {

    /**
     * SQL statement used to delete a specific order item.
     */
    private static final String STATEMENT =
            "DELETE FROM lounge.OrderItem WHERE drink_name = ? AND orderID = ?";

    /**
     * Name of the drink to be removed from the order.
     */
    private final String drinkName;

    /**
     * Identifier of the order containing the item.
     */
    private final long orderID;

    /**
     * Creates a new DAO for deleting an item from an order.
     *
     * @param connection the database connection
     * @param drinkName the name of the drink to delete
     * @param orderID the ID of the order
     * @throws IllegalArgumentException if {@code drinkName} is null/empty or {@code orderID} is not positive
     */
    public DeleteOrderItemDAO(final Connection connection, final String drinkName, final long orderID) {
        super(connection);

        if (drinkName == null || drinkName.isBlank()) {
            LOGGER.error("Invalid drink name.");
            throw new IllegalArgumentException("Drink name cannot be null or empty.");
        }

        if (orderID <= 0) {
            LOGGER.error("Invalid order ID.");
            throw new IllegalArgumentException("Order ID must be positive.");
        }

        this.drinkName = drinkName;
        this.orderID = orderID;
    }

    /**
     * Executes the deletion of the specified order item.
     *
     * <p>The method:
     * <ul>
     *     <li>Prepares the SQL DELETE statement</li>
     *     <li>Binds the drink name and order ID</li>
     *     <li>Executes the update</li>
     *     <li>Checks how many rows were affected</li>
     * </ul>
     *
     * <p>If no rows are affected, it means the item was not found in the order.</p>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {

            // Bind parameters: drink name and order ID
            ps.setString(1, drinkName);
            ps.setLong(2, orderID);

            // Execute deletion and check affected rows
            int affectedRows = ps.executeUpdate();

            // If no rows were deleted, the item does not exist
            if (affectedRows == 0) {
                LOGGER.warn("No item '{}' found in order {}.", drinkName, orderID);
                outputParam = false;
                return;
            }

            // Log successful deletion
            LOGGER.info("Item '{}' removed from order {}.", drinkName, orderID);

            outputParam = true;
        }
    }
}