package dei.unipd.it.database;

import dei.unipd.it.resources.PayMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for marking an order as paid.
 *
 * <p>This class updates an existing order by:
 * <ul>
 *     <li>Setting the payment method</li>
 *     <li>Changing the order status to {@code PAID}</li>
 * </ul>
 * </p>
 *
 * <p>The operation returns:
 * <ul>
 *     <li>{@code true} if the payment was successfully applied</li>
 *     <li>{@code false} if no order with the given ID exists</li>
 * </ul>
 * </p>
 */
public final class PayOrderDAO extends AbstractDAO<Boolean> {

    /**
     * SQL statement used to update the payment method and set the order as PAID.
     */
    private static final String STATEMENT =
            "UPDATE lounge.Orders SET pay_method = ?, status = 'PAID' WHERE id = ?";

    /**
     * Identifier of the order to be paid.
     */
    private final int orderID;

    /**
     * Payment method used for the order.
     */
    private final PayMethod payMethod;

    /**
     * Creates a new DAO for processing the payment of an order.
     *
     * @param conn the database connection
     * @param orderID the ID of the order to be paid
     * @param payMethod the payment method to apply
     * @throws IllegalArgumentException if {@code orderID} is not positive
     * @throws NullPointerException if {@code payMethod} is null
     */
    public PayOrderDAO(final Connection conn, final int orderID, final PayMethod payMethod) {
        super(conn);

        if (orderID <= 0) {
            LOGGER.error("Invalid order ID.");
            throw new IllegalArgumentException("Order ID must be positive.");
        }

        if (payMethod == null) {
            LOGGER.error("Payment method cannot be null.");
            throw new NullPointerException("Payment method cannot be null.");
        }

        this.orderID = orderID;
        this.payMethod = payMethod;
    }

    /**
     * Executes the payment operation.
     *
     * <p>The method:
     * <ul>
     *     <li>Updates the payment method</li>
     *     <li>Sets the order status to {@code PAID}</li>
     *     <li>Checks if the order exists using the number of affected rows</li>
     * </ul>
     *
     * <p>If no rows are affected, it means the order does not exist.</p>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected void doAccess() throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {

            // Bind parameters: payment method and order ID
            ps.setString(1, payMethod.toString());
            ps.setInt(2, orderID);

            // Execute update and check affected rows
            int affectedRows = ps.executeUpdate();

            // If no rows were updated, the order does not exist
            if (affectedRows == 0) {
                LOGGER.warn("No order found with ID {}. Payment not applied.", orderID);
                outputParam = false;
                return;
            }

            // Log successful payment
            LOGGER.info("Order {} successfully paid using {}.", orderID, payMethod);

            outputParam = true;
        }
    }
}