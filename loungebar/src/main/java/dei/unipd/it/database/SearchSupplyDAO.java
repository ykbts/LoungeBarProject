package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SearchSupplyDAO extends AbstractDAO<List<Supply>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Supply";

    private final String name;
    private final int unitsAvailable;

    public SearchSupplyDAO(final Connection con, String name, int unitsAvailable) {
        super(con);
        this.name = name;
        this.unitsAvailable = unitsAvailable;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<Supply> supplyList = new ArrayList<Supply>();
        final List<String> clauses= new ArrayList<String>();


        if(name != null ){ clauses.add(" lounge.Supply.name = ? "); }
        if(unitsAvailable >= 0 ){ clauses.add(" lounge.Supply.units_available = ? "); }

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

            if(name != null) {
                pstmt.setString(clauseCount, name);
                clauseCount += 1;
            }
            if(unitsAvailable >= 0 ){
                pstmt.setInt(clauseCount, unitsAvailable);
                clauseCount+=1;
            }


            rs = pstmt.executeQuery();

            while (rs.next()) {
                supplyList.add(new Supply(
                        rs.getString("name"),
                        rs.getInt("units_available")));
            }

            LOGGER.info("Supply(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = supplyList;
    }
}
