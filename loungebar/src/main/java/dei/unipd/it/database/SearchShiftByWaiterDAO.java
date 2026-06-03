package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for searching shifts by waiter ID.
 *
 * <p>Returns a list of {@link Shift} objects for a specific waiter.</p>
 */
public final class SearchShiftByWaiterDAO extends AbstractDAO<List<Shift>> {

    /**
     * SQL statement used to retrieve shifts for a specific waiter via the work assignment table.
     */
    private static final String STATEMENT =
            "SELECT s.id, s.start_time, s.end_time " +
                    "FROM lounge.Shift s " +
                    "JOIN lounge.Work w ON s.id = w.shiftID " +
                    "WHERE w.user_waiterID = ?";

    /**
     * The identifier of the waiter whose shifts are being searched.
     */
    private final String waiterID;

    /**
     * Creates a new DAO for searching shifts by waiter.
     *
     * @param con the database connection
     * @param waiterID the ID of the waiter to search for
     */
    public SearchShiftByWaiterDAO(final Connection con, final String waiterID) {
        super(con);
        this.waiterID = waiterID;
    }

    /**
     * Retrieves shifts for a waiter from the database.
     *
     * <p>Executes the query, maps results to {@link Shift}, and returns the list.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Shift> shifts = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, waiterID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                shifts.add(new Shift(
                        rs.getInt("id"),
                        rs.getObject("start_time", LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class)
                ));
            }

            LOGGER.info("Shifts successfully retrieved for waiter: {}", waiterID);

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