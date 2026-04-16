package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateShiftDAO extends AbstractDAO<Shift> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Shift (startTime, endTime) VALUES (?, ?) RETURNING *";
    private final Shift shift;

    public CreateShiftDAO(final Connection connection, final Shift shift) {
        super(connection);
        if (shift == null) {
            LOGGER.error("The shift cannot be null.");
            throw new NullPointerException("The shift cannot be null.");
        }
        this.shift = shift;
    }

    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Shift u = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setObject(1, shift.getStartTime());
            preparedStatement.setObject(2, shift.getEndTime());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                u = new Shift(
                        rs.getInt("id"),
                        rs.getObject("startTime" , LocalDateTime.class),
                        rs.getObject("endTime", LocalDateTime.class));

                LOGGER.info("Shift {} successfully stored.", u.getId());
            }
        }

        outputParam = u;

    }
}



