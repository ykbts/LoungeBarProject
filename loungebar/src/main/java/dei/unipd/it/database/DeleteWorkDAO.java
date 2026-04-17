package dei.unipd.it.database;

import dei.unipd.it.resources.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteWorkDAO extends AbstractDAO<Work> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Work WHERE user_waiterID = ? AND shiftID = ? RETURNING *;";

    private final Work work;

    public DeleteWorkDAO(final Connection con, final Work work) {
        super(con);

        if (work == null) {
            LOGGER.error("The work object cannot be null");
            throw new NullPointerException("The work object cannot be null");
        }

        if (work.getWaiterID() == null || work.getWaiterID().isBlank()) {
            LOGGER.error("Invalid weiter ID provided for deletion");
            throw new IllegalArgumentException("Invalid weiter ID provided");
        }

        if (work.getShiftID() <= 0) {
            LOGGER.error("Invalid shift ID provided for deletion");
            throw new IllegalArgumentException("Invalid shift ID provided");
        }

        this.work = work;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Work deletedWork = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, work.getWaiterID());
            pstmt.setInt(2, work.getShiftID());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedWork = new Work(
                        rs.getString("user_waiterID"),
                        rs.getInt("shiftID")
                );

                LOGGER.info("Work assignment for waiter {} in shift {} successfully deleted", deletedWork.getWaiterID(), deletedWork.getShiftID());
            } else {
                LOGGER.warn("Nothing was deleted", work.getWaiterID(), work.getShiftID());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedWork;
    }
}