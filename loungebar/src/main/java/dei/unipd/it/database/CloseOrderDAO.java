package dei.unipd.it.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for closing an order.
 *
 * <p>This class enforces a simple business rule: an order can only be closed
 * if its current status is {@code PAID}. This ensures a correct workflow
 * and prevents invalid state transitions.</p>
 *
 * <p>The operation returns:
 * <ul>
 *     <li>{@code true} if the order is successfully closed</li>
 *     <li>{@code false} if the order does not exist or cannot be closed</li>
 * </ul>
 * </p>
 */
public final class CloseOrderDAO extends AbstractDAO<Boolean> {

    /**
     * SQL statement used to retrieve the current status of the order.
     */
    private static final String CHECK_STATUS =
            "SELECT status FROM lounge.Orders WHERE id = ?";

    /**
     * SQL statement used to update the order status to CLOSED.
     */
    private static final String UPDATE_STATUS =
            "UPDATE lounge.Orders SET status = 'CANCELED' WHERE id = ?";
    /**
     * Identifier of the order to be closed.
     */
    private final int orderID;

    /**
     * Creates a new DAO for closing an order.
     *
     * @param conn the database connection
     * @param orderID the ID of the order to close
     * @throws IllegalArgumentException if {@code orderID} is not positive
     */
    public CloseOrderDAO(final Connection conn, final int orderID) {
        super(conn);

        if (orderID <= 0) {
            LOGGER.error("Invalid order ID.");
            throw new IllegalArgumentException("Order ID must be positive.");
        }

        this.orderID = orderID;
    }

    /**
     * Executes the closing process.
     *
     * <p>The method performs the following steps:
     * <ol>
     *     <li>Retrieves the current status of the order</li>
     *     <li>Checks whether the order is in {@code PAID} state</li>
     *     <li>If valid, updates the status to {@code CLOSED}</li>
     * </ol>
     *
     * <p>If the order does not exist or is not in the correct state,
     * the operation is aborted and {@code false} is returned.</p>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement psCheck = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            // Step 1: retrieve current status of the order
            psCheck = conn.prepareStatement(CHECK_STATUS);
            psCheck.setInt(1, orderID);

            rs = psCheck.executeQuery();

            // If no record is found, the order does not exist
            if (!rs.next()) {
                LOGGER.warn("Order {} not found.", orderID);
                outputParam = false;
                return;
            }

            String currentStatus = rs.getString("status");

            // Step 2: ensure order is in PAID state before closing
            if (!"PAID".equals(currentStatus)) {
                LOGGER.warn("Order {} cannot be closed. Current status: {}", orderID, currentStatus);
                outputParam = false;
                return;
            }

            // Step 3: update status to CLOSED
            psUpdate = conn.prepareStatement(UPDATE_STATUS);
            psUpdate.setInt(1, orderID);

            psUpdate.executeUpdate();

            LOGGER.info("Order {} successfully closed.", orderID);

            outputParam = true;

        } catch (SQLException e) {
            // Log and propagate the exception
            LOGGER.error("Error while closing order {}.", orderID, e);
            throw e;
        } finally {
            // Ensure all resources are properly closed
            if (rs != null) rs.close();
            if (psCheck != null) psCheck.close();
            if (psUpdate != null) psUpdate.close();
        }
    }
}