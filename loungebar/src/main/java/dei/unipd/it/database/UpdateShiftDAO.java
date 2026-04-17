package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UpdateShiftDAO extends AbstractDAO<Shift> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE lounge.Shift SET start_time = ?, end_time = ? WHERE id = ?;";

    private final Shift shift;

    public UpdateShiftDAO(final Connection con, final Shift shift) {
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

        try {
            pstmt = conn.prepareStatement(STATEMENT);

            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(shift.getStartTime()));
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(shift.getEndTime()));
            pstmt.setInt(3, shift.getId());

            pstmt.execute();

            LOGGER.info("Shift {} successfully updated.", shift.getId());

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}

 
  
