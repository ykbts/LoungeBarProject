package dei.unipd.it.database;

import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.OrderState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for updating the status of an existing order.
 *
 * <p>This class updates only the {@code status} field of an order identified by its ID.
 * It performs basic validation on input parameters and ensures that the update
 * operation affects an existing record.</p>
 *
 * <p>If the update is successful, a minimal {@link Orders} object is returned
 * through {@code outputParam}. If no order is found, {@code null} is returned.</p>
 */
public final class UpdateOrderStatusDAO extends AbstractDAO<Orders> {

    /**
     * SQL statement used to update the status of an order.
     */
    private static final String STATEMENT =
            "UPDATE lounge.Orders SET status = ? WHERE id = ?;";

    /**
     * Identifier of the order to be updated.
     */
    private final int orderID;

    /**
     * New status to assign to the order.
     */
    private final OrderState status;

    /**
     * Creates a new DAO for updating the status of an order.
     *
     * @param conn the database connection
     * @param orderID the ID of the order to update
     * @param status the new status to set
     * @throws IllegalArgumentException if {@code orderID} is not positive
     * @throws NullPointerException if {@code status} is null
     */
    public UpdateOrderStatusDAO(final Connection conn, final int orderID, final OrderState status) {
        super(conn);

        if (orderID <= 0) {
            LOGGER.error("Invalid order ID.");
            throw new IllegalArgumentException("Order ID must be positive.");
        }

        if (status == null) {
            LOGGER.error("Status cannot be null.");
            throw new NullPointerException("Status cannot be null.");
        }

        this.orderID = orderID;
        this.status = status;
    }

    /**
     * Executes the update operation on the database.
     *
     * <p>The method:
     * <ul>
     *     <li>Prepares the SQL statement</li>
     *     <li>Binds parameters (status and order ID)</li>
     *     <li>Executes the update</li>
     *     <li>Checks if any row was affected</li>
     * </ul>
     *
     * <p>If no rows are affected, it means the order does not exist.</p>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;

        try {
            // Prepare the SQL statement
            ps = conn.prepareStatement(STATEMENT);

            // Bind parameters: new status and order ID
            ps.setString(1, status.toString());
            ps.setInt(2, orderID);

            // Execute update and retrieve number of affected rows
            int affectedRows = ps.executeUpdate();

            // If no rows were updated, the order does not exist
            if (affectedRows == 0) {
                LOGGER.warn("No order found with ID {}. No update performed.", orderID);
                outputParam = null;
                return;
            }

            // Log successful update
            LOGGER.info("Order {} status successfully updated to {}.", orderID, status);

            // Return a minimal representation of the updated order
            outputParam = new Orders(orderID, null, status, null, null, 0);

        } catch (SQLException e) {
            // Log and propagate the exception for higher-level handling
            LOGGER.error("Error while updating order {} status.", orderID, e);
            throw e;
        } finally {
            // Ensure the statement is always closed
            if (ps != null) {
                ps.close();
            }
        }
    }
}