package dei.unipd.it.database;

import dei.unipd.it.resources.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ListAvailableTablesDAO extends AbstractDAO<List<Tables>> {

    private static final String STATEMENT = "SELECT * FROM lounge.Tables WHERE is_available = true";

    /**
     * create a new DAO for listing all tables available at the moment
     *
     * @param conn the connection to the database
     */
    public ListAvailableTablesDAO(final Connection conn) {
        super(conn);
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        // the available tables
        List<Tables> availableTables = new ArrayList<>();

        try {
            ps = conn.prepareStatement(STATEMENT);

            rs = ps.executeQuery();

            while (rs.next()) {
                availableTables.add( new Tables(
                        rs.getInt("number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_available")
                ));
            }

            LOGGER.info("Found {} available table(s).", availableTables.size());
        }
        catch (SQLException e) {
            LOGGER.error("Cannot retrieve available tables: unexpected error.", e);
        }
        finally {
            if (rs != null)     rs.close();
            if (ps != null)     ps.close();
        }

        outputParam = availableTables;
    }
}
