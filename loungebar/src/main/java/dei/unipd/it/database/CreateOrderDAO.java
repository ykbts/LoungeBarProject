package dei.unipd.it.database;

import dei.unipd.it.resources.OrderState;
import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.OrderItem;
import dei.unipd.it.resources.PayMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateOrderDAO extends AbstractDAO<Orders> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Orders (dateTime, status, payMethod, waiterID, tableID) VALUES (?, ?, ?, ?, ?) RETURNING *";
    private final Orders orders;

    public CreateOrderDAO (final Connection connection, final Orders orders) {
        super(connection);

        if (orders == null) {
            LOGGER.error("The orders cannot be null.");
            throw new NullPointerException("The orders cannot be null.");
        }
        this.orders = orders;
    }

    /** prepare and execute the query */
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Orders o = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, orders.getDateTime().toString());
            preparedStatement.setString(2, orders.getStatus().toString());
            preparedStatement.setString(3, orders.getPayMethod().toString());
            preparedStatement.setString(4, orders.getWaiterID());
            preparedStatement.setInt(5, orders.getTableID());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                o = new Orders (rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("dateTime")),
                        OrderState.valueOf(rs.getString("status")),
                        PayMethod.valueOf(rs.getString("payMethod")),
                        rs.getString("waiterID"),
                        rs.getInt("tableID"));

                LOGGER.info("Order {} successfully stored.", o.getId());
            }
        }

        outputParam = o;
    }
}