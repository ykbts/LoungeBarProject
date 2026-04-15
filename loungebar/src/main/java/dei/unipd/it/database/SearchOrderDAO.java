package dei.unipd.it.database;

import dei.unipd.it.resources.OrderState;
import dei.unipd.it.resources.Orders;
import dei.unipd.it.resources.PayMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SearchOrderDAO extends AbstractDAO<List<Orders>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Orders";

    private final long id;
    private final LocalDateTime dateTime;
    private final String status;
    private final String payMethod;
    private final String waiterID;
    private final int tableID;


    public SearchOrderDAO(final Connection con, long id, LocalDateTime dateTime, String status, String payMethod, String waiterID, int tableID) {
        super(con);
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.payMethod = payMethod;
        this.waiterID = waiterID;
        this.tableID = tableID;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<Orders> ordersList = new ArrayList<Orders>();
        final List<String> clauses= new ArrayList<String>();


        if(id>=0){ clauses.add(" lounge.Orders.id = ? "); }
        if(dateTime != null ){ clauses.add(" lounge.Orders.dateTime = ? "); }
        if(status != null ){ clauses.add(" lounge.Orders.status = ? "); }
        if(payMethod != null){ clauses.add(" lounge.Orders.payMethod = ? "); }
        if(waiterID != null ){ clauses.add(" lounge.Orders.waiterID = ? "); }
        if(tableID>=0){ clauses.add(" lounge.Orders.tableID = ? "); }

        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");
        if(clauses.size()>0){
            sb.append(" WHERE ");
            boolean first= true;
            for(String s : clauses){
                if(first){
                    first=false;
                    sb.append(s);
                }else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");




        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;

            if(id>=0) {
                pstmt.setLong(clauseCount, id);
                clauseCount += 1;
            }
            if(dateTime!=null){
                pstmt.setObject(clauseCount, dateTime);
                clauseCount+=1;
            }
            if(status!=null){
                pstmt.setString(clauseCount, status);
                clauseCount+=1;
            }
            if(payMethod!=null) {
                pstmt.setString(clauseCount, payMethod);
                clauseCount += 1;
            }
            if(waiterID!=null) {
                pstmt.setString(clauseCount, waiterID);
                clauseCount += 1;
            }
            if(tableID>=0) {
                pstmt.setInt(clauseCount, tableID);
                clauseCount += 1;
            }




            rs = pstmt.executeQuery();

            while (rs.next()) {
                ordersList.add(new Orders(rs.getInt("id"),
                        rs.getObject("dateTime", LocalDateTime.class),
                        OrderState.valueOf(rs.getString("status")),
                        PayMethod.valueOf(rs.getString("payMethod")),
                        rs.getString("waiterID"),
                        rs.getInt("tableID")));
            }

            LOGGER.info("Order(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = ordersList;
    }
}
