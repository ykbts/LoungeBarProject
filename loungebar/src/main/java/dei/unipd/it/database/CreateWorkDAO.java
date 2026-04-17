package dei.unipd.it.database;

import dei.unipd.it.resources.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateWorkDAO extends AbstractDAO<Work> {

    private static final String STATEMENT = "INSERT INTO lounge.Work (WaiterID, shiftID) VALUES (?, ?) RETURNING *";
    private final Work work;

    public CreateWorkDAO (final Connection connection, final Work work) {
        super(connection);

        if (work == null) {
            LOGGER.error("The work cannot be null.");
            throw new NullPointerException("The work cannot be null.");
        }
        this.work = work;
    }

    /** prepare and execute the query */
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Work r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, work.getWaiterID());
            preparedStatement.setInt(2, work.getShiftID());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Work (
                        rs.getString("waiterID"),
                        rs.getInt("shiftID"));

                LOGGER.info("Work {} successfully stored.", r.getWaiterID(), r.getShiftID());
            }
        }

        outputParam = r;
    }
}

