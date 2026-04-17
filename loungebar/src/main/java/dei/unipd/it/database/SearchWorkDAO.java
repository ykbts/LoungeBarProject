package dei.unipd.it.database;

import dei.unipd.it.resources.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchWorkDAO extends AbstractDAO<List<Work>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Work";

    private final String waiterID;
    private final int shiftID;


    public SearchWorkDAO(final Connection con, String waiterID, int shiftID) {
        super(con);
        this.waiterID = waiterID;
        this.shiftID = shiftID;
    }

    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<Work> workList = new ArrayList<>();
        final List<String> clauses= new ArrayList<>();


        if(waiterID != null){ clauses.add(" lounge.Work.waiterID = ? "); }
        if(shiftID >=0){ clauses.add(" lounge.Work.shiftID = ? "); }

        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");

        if(!clauses.isEmpty()){
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

            if(waiterID != null) {
                pstmt.setString(clauseCount, waiterID);
                clauseCount += 1;
            }
            if(shiftID >=0) {
                pstmt.setInt(clauseCount, shiftID);
                clauseCount+=1;
            }


            rs = pstmt.executeQuery();

            while (rs.next()) {

                workList.add(new Work(
                        rs.getString("waiterID"),
                        rs.getInt("shiftID")));
            }

            LOGGER.info("Work(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = workList;
    }
}
