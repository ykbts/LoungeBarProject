package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;
import dei.unipd.it.resources.SupplyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for searching supplies.
 *
 * <p>Filters supplies by name or stock quantity.</p>
 *
 * <p>Returns a list of matching {@link Supply} objects.</p>
 */
public final class SearchSupplyDAO extends AbstractDAO<List<Supply>> {

    /**
     * The base SQL statement to be executed.
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Supply";

    /**
     * The name filter for the supply search.
     */
    private final String name;

    /**
     * The units available filter for the supply search.
     */
    private final int unitsAvailable;

    /**
     * The type filter for the supply search.
     */
    private final SupplyType type;

    /**
     * Creates a new DAO for searching supplies with optional filters.
     *
     * @param con the database connection
     * @param name the name (or part of it) to search for; if {@code null}, this filter is ignored
     * @param unitsAvailable the number of units to match; if negative, this filter is ignored
     */
    public SearchSupplyDAO(final Connection con, String name, final int unitsAvailable, final  SupplyType type) {
        super(con);
        this.name = name;
        this.unitsAvailable = unitsAvailable;
        this.type = type;
    }

    /**
     * Searches supplies in the database.
     *
     * <p>Builds filters dynamically, executes the query, and returns matching {@link Supply} objects.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Supply> supplyList = new ArrayList<Supply>();
        final List<String> clauses = new ArrayList<String>();


        if(name != null ){ clauses.add(" LOWER(lounge.Supply.name) LIKE LOWER(?) "); }
        if(unitsAvailable >= 0 ){ clauses.add(" lounge.Supply.units_available >= ? "); }
        if(type != null){ clauses.add(" lounge.Supply.type = ? "); }

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
                pstmt.setString(clauseCount, "%" + name + "%");
                clauseCount += 1;
            }
            if (unitsAvailable >= 0) {
                pstmt.setInt(clauseCount, unitsAvailable);
                clauseCount += 1;
            }
            if (type != null) {
                pstmt.setString(clauseCount, type.toString());
                clauseCount += 1;
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                supplyList.add(new Supply(
                        rs.getString("name"),
                        rs.getInt("units_available"),
                        SupplyType.valueOf(rs.getString("type"))
                    )
                );
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