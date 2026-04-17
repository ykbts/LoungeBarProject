package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteShiftDAO extends AbstractDAO<Shift> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Shift WHERE id = ? RETURNING *;";

    private final Shift shift;

    public DeleteShiftDAO(final Connection con, final Shift shift) {
        super(con);

        if (shift == null) {
            LOGGER.error("The shift cannot be null");
            throw new NullPointerException("The shift cannot be null");
        }

        if (shift.getId() <= 0) {
            LOGGER.error("The shift ID must be a positive integer");
            throw new IllegalArgumentException("The shift ID must be a positive integer");
        }

        this.shift = shift;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Shift deletedShift = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setInt(1, shift.getId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedShift = new Shift(
                        rs.getInt("id"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime()
                );

                LOGGER.info("Shift {} successfully deleted.", deletedShift.getId());
            } else {
                LOGGER.warn("Nothing was deleted for shift ID {}", shift.getId());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedShift;
    }
}

