package dei.unipd.it.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for deleting an order and all its associated items.
 *
 * <p>This class ensures that all {@code OrderItem} entries related to a given
 * order are deleted before removing the order itself. This prevents potential
 * foreign key constraint violations in the database.</p>
 *
 * <p>The operation returns a boolean value:
 * <ul>
 *     <li>{@code true} if the order was successfully deleted</li>
 *     <li>{@code false} if no order with the given ID was found</li>
 * </ul>
 * </p>
 */
public final class DeleteOrderDAO extends AbstractDAO<Boolean> {

    /**
     * SQL statement used to delete all items associated with an order.
     */
    private static final String DELETE_ITEMS =
            "DELETE FROM lounge.OrderItem WHERE orderID = ?";

    /**
     * SQL statement used to delete the order itself.
     */
    private static final String DELETE_ORDER =
            "DELETE FROM lounge.Orders WHERE id = ?";

    /**
     * Identifier of the order to be deleted.
     */
    private final int orderID;

    /**
     * Creates a new DAO for deleting an order.
     *
     * @param connection the database connection
     * @param orderID the ID of the order to delete
     * @throws IllegalArgumentException if {@code orderID} is not positive
     */
    public DeleteOrderDAO(final Connection connection, final int orderID) {
        super(connection);

        if (orderID <= 0) {
            LOGGER.error("Invalid order ID.");
            throw new IllegalArgumentException("Order ID must be positive.");
        }

        this.orderID = orderID;
    }

    /**
     * Executes the deletion process.
     *
     * <p>The method performs two steps:
     * <ol>
     *     <li>Deletes all {@code OrderItem} records linked to the order</li>
     *     <li>Deletes the order itself</li>
     * </ol>
     *
     * <p>The first step is necessary to avoid foreign key constraint violations.</p>
     *
     * <p>If no rows are affected during the order deletion, it means that the
     * order does not exist in the database.</p>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement psItems = null;
        PreparedStatement psOrder = null;

        try {
            // Step 1: delete all items associated with the order
            // This prevents foreign key constraint issues when deleting the order
            psItems = conn.prepareStatement(DELETE_ITEMS);
            psItems.setInt(1, orderID);
            psItems.executeUpdate();

            // Step 2: delete the order itself
            psOrder = conn.prepareStatement(DELETE_ORDER);
            psOrder.setInt(1, orderID);

            int affectedRows = psOrder.executeUpdate();

            // If no rows were deleted, the order does not exist
            if (affectedRows == 0) {
                LOGGER.warn("No order found with ID {}. Nothing deleted.", orderID);
                outputParam = false;
                return;
            }

            // Log successful deletion
            LOGGER.info("Order {} and its items successfully deleted.", orderID);

            outputParam = true;

        } catch (SQLException e) {
            // Log and propagate exception for higher-level handling
            LOGGER.error("Error while deleting order {}.", orderID, e);
            throw e;
        } finally {
            // Ensure all resources are properly closed
            if (psItems != null) psItems.close();
            if (psOrder != null) psOrder.close();
        }
    }
}