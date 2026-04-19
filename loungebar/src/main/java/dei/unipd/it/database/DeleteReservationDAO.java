package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public final class DeleteReservationDAO extends AbstractDAO<Reservation> {

    private static final String STATEMENT = "DELETE FROM lounge.Reservation WHERE id = ? RETURNING *";
    private final int badge;
    /**
     * create a new DAO for deleting a reservation
     *
     * @param conn   the connection to the database
     * @param badge  the identifier of the reservation to be deleted
     */
    public DeleteReservationDAO(Connection conn, final int badge) {
        super(conn);
        this.badge = badge;
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        // the deleted reservation
        Reservation r = null;

        try {
            ps = conn.prepareStatement(STATEMENT);
            ps.setInt(1, badge);

            rs = ps.executeQuery();

            if  (rs.next()) {
                r = new Reservation(
                        rs.getInt("id"),
                        rs.getObject("start_time", LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class),
                        rs.getInt("n_people"),
                        rs.getString("user_customerID"),
                        rs.getInt("tableNumber"));

                LOGGER.info("Reservation {} successfully deleted.", r.getId());
            }
        }
        catch (SQLException e) {
            LOGGER.error("Cannot delete reservation: unexpected error.");
        }
        finally {
            if (ps != null)     ps.close();
            if (rs != null)     rs.close();
        }

        outputParam = r;
    }
}
