package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * DAO class for creating a reservation.
 *
 * <p>Saves reservation details: time, number of people, table and customer.</p>
 *
 * <p>Returns the created {@link Reservation} with its ID.</p>
 */
public class CreateReservationDAO extends AbstractDAO<Reservation> {

    /**
     * SQL statement used to insert a new reservation and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.Reservation (start_time, end_time, n_people, tableNumber, user_customerID) VALUES (?, ?, ?, ?, ?) RETURNING *";

    /**
     * The reservation data to be persisted.
     */
    private final Reservation reservation;

    /**
     * Creates a new DAO for inserting a specific reservation.
     *
     * @param connection the database connection
     * @param reservation the reservation object containing the data to be stored
     * @throws NullPointerException if {@code reservation} is null
     */
    public CreateReservationDAO (final Connection connection, final Reservation reservation) {
        super(connection);

        if (reservation == null) {
            LOGGER.error("The reservation cannot be null.");
            throw new NullPointerException("The reservation cannot be null.");
        }
        this.reservation = reservation;
    }

    /**
     * Creates a reservation in the database.
     *
     * <p>Sets reservation data, executes the insert, and returns the saved {@link Reservation}.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Reservation r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setObject(1, reservation.getStartTime());
            preparedStatement.setObject(2, reservation.getEndTime());
            preparedStatement.setInt(3, reservation.getPeople());
            preparedStatement.setInt(4, reservation.getTable());
            preparedStatement.setString(5, reservation.getCustomer());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Reservation (rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("start_time").replace(" ", "T")),
                        LocalDateTime.parse(rs.getString("end_time").replace(" ", "T")),
                        rs.getInt("n_people"),
                        rs.getString("user_customerID"),
                        rs.getInt("tableNumber"));

                LOGGER.info("Reservation {} successfully stored.", r.getId());
            }
        }

        outputParam = r;
    }
}