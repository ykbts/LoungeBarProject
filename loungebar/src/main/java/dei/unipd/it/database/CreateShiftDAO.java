package dei.unipd.it.database;

import dei.unipd.it.resources.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * DAO class for creating a work shift.
 *
 * <p>Saves shift start and end time.</p>
 *
 * <p>Returns the created {@link Shift} with its ID.</p>
 */
public class CreateShiftDAO extends AbstractDAO<Shift> {

    /** * SQL statement used to insert a new shift and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.Shift (start_time, end_time) VALUES (?, ?) RETURNING *";

    /**
     * The shift data to be persisted.
     */
    private final Shift shift;

    /**
     * Creates a new DAO for inserting a specific shift.
     *
     * @param connection the database connection
     * @param shift the shift object containing the timestamps to be stored
     * @throws NullPointerException if {@code shift} is null
     */
    public CreateShiftDAO(final Connection connection, final Shift shift) {
        super(connection);
        if (shift == null) {
            LOGGER.error("The shift cannot be null.");
            throw new NullPointerException("The shift cannot be null.");
        }
        this.shift = shift;
    }

    /**
     * Creates a shift in the database.
     *
     * <p>Sets start and end time, executes the insert, and returns the saved {@link Shift}.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Shift s = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setObject(1, shift.getStartTime());
            preparedStatement.setObject(2, shift.getEndTime());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                s = new Shift(
                        rs.getInt("id"),
                        rs.getObject("start_time" , LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class));

                LOGGER.info("Shift {} successfully stored.", s.getId());
            }
        }

        outputParam = s;
    }
}