package dei.unipd.it.database;

import dei.unipd.it.resources.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteTableDAO extends AbstractDAO<Tables>{

    private static final String STATEMENT = "DELETE FROM lounge.Tables WHERE number = ? RETURNING *";
    private final int number;

    /**
     * create a new DAO for deleting a table
     *
     * @param conn   the connection to the database
     * @param number  the identifier of the reservation to be deleted
     */
    public DeleteTableDAO(Connection conn, final int number) {
        super(conn);
        this.number = number;
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        // the deleted table
        Tables t = null;

        try {
            ps = conn.prepareStatement(STATEMENT);
            ps.setInt(1, number);

            rs = ps.executeQuery();

            if (rs.next()){
                t = new Tables(
                        rs.getInt("number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_available")
                );

                LOGGER.info("Table {} successfully deleted.", t.getNumber());
            }
        }
        catch (SQLException e) {
            LOGGER.error("Cannot delete reservation: unexpected error.");
        }
        finally {
            if (ps != null)     ps.close();
            if (rs != null)     rs.close();
        }

        outputParam = t;
    }
}
