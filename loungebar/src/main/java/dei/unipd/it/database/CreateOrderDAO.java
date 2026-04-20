package dei.unipd.it.database;

import dei.unipd.it.resources.OrderState;
import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.PayMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateOrderDAO extends AbstractDAO<Orders> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Orders (date_time, status, pay_method, user_waiterID, tableID) VALUES (?, ?, ?, ?, ?) RETURNING *";
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

            preparedStatement.setObject(1, orders.getDateTime());
            preparedStatement.setString(2, orders.getStatus().toString());
            preparedStatement.setString(3, orders.getPayMethod() != null ? orders.getPayMethod().toString() : null);
            preparedStatement.setString(4, orders.getWaiterID());
            preparedStatement.setInt(5, orders.getTableID());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                o = new Orders (rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("date_time")),
                        OrderState.valueOf(rs.getString("status")),
                        rs.getString("pay_method") != null ? PayMethod.valueOf(rs.getString("pay_method")) : null,
                        rs.getString("user_waiterID"),
                        rs.getInt("tableID"));

                LOGGER.info("Order {} successfully stored.", o.getId());
            }
        }

        outputParam = o;
    }
}