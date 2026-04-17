package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SearchShiftDAO extends AbstractDAO<List<Shift>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Shift";

    private final int id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;


    public SearchShiftDAO(final Connection con, int id, LocalDateTime startTime, LocalDateTime endTime) {
        super(con);
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<Shift> shifts = new ArrayList<>();
        final List<String> clauses= new ArrayList<>();


        if(id>=0){ clauses.add(" lounge.Shift.id = ? "); }
        if(startTime != null ){ clauses.add(" lounge.Shift.start_time = ? "); }
        if(endTime != null ){ clauses.add(" lounge.Shift.end_time = ? "); }

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

            if(id>=0) {
                pstmt.setInt(clauseCount, id);
                clauseCount += 1;
            }
            if(startTime!=null){
                pstmt.setObject(clauseCount, startTime);
                clauseCount+=1;
            }
            if(endTime!=null){
                pstmt.setObject(clauseCount, endTime);
                clauseCount+=1;
            }



            rs = pstmt.executeQuery();

            while (rs.next()) {
                shifts.add(new Shift(
                        rs.getInt("id"),
                        rs.getObject("start_time", LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class)
                ));			}

            LOGGER.info("Shift(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = shifts;
    }
}