package dei.unipd.it.database;

import dei.unipd.it.resources.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchOrderItemsByTableDAO extends AbstractDAO<List<OrderItem>> {

    private final static String STATEMENT = "SELECT oi.orderID, oi.drink_name, oi.quantity " +
            "FROM lounge.OrderItem oi " +
            "JOIN lounge.Orders o ON o.id = oi.orderID " +
            "WHERE o.tableID = ? AND o.status = 'PENDING';";

    private final int table;

    /**
     * Creates a new DAO for searching the items of an order given the table number
     *
     * @param conn  the connection to be used for accessing the database.
     * @param table the number of the table associated with the order
     */
    public SearchOrderItemsByTableDAO(Connection conn, int table) {
        super(conn);
        this.table = table;
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        final List<OrderItem> orderItems = new ArrayList<>();

        try {
            ps = conn.prepareStatement(STATEMENT);

            ps.setInt(1, table);

            rs = ps.executeQuery();

            while (rs.next()) {
                orderItems.add(new OrderItem(
                        rs.getString("drink_name"),
                        rs.getInt("orderID"),
                        rs.getInt("quantity")
                ));
            }
            LOGGER.info("OrderItem(s) successfully listed.");
        }
        finally {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
    }
}
