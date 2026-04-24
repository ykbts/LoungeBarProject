package dei.unipd.it.database;

import dei.unipd.it.resources.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchOrderItemDAO extends AbstractDAO<List<OrderItem>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.OrderItem";

    private final String drinkName;
    private final int orderID;
    private final int quantity;


    public SearchOrderItemDAO(final Connection con, String drinkName, int orderID, int quantity) {
        super(con);
        this.drinkName = drinkName;
        this.orderID = orderID;
        this.quantity = quantity;
    }

    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<OrderItem> orderItems = new ArrayList<>();
        final List<String> clauses= new ArrayList<>();


        if (drinkName != null && !drinkName.isEmpty()) { clauses.add(" lounge.OrderItem.drink_name = ? ");}
        if (orderID >= 0) { clauses.add(" lounge.OrderItem.orderID = ? ");}
        if (quantity >= 0) { clauses.add(" lounge.OrderItem.quantity = ? ");}

        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");

        if(!clauses.isEmpty()){

            sb.append(" WHERE ");
            boolean first= true;

            for(String s : clauses){

                if (first){
                    first=false;
                    sb.append(s);
                }
                else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");

        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;

            if (drinkName != null && !drinkName.isEmpty()) {
                pstmt.setString(clauseCount++, drinkName);
            }
            if (orderID >= 0) {
                pstmt.setLong(clauseCount++, orderID);
            }
            if (quantity >= 0) {
                pstmt.setInt(clauseCount++, quantity);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderItems.add(new OrderItem(
                        rs.getString("drink_name"),
                        rs.getInt("orderID"),
                        rs.getInt("quantity")
                ));
            }

            LOGGER.info("OrderItem(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = orderItems;
    }
}
