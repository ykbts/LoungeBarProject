package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateReservationDAO extends AbstractDAO<Reservation> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Reservation (start_time, end_time, n_people, tableNumber, user_customerID) VALUES (?, ?, ?, ?, ?) RETURNING *";
    private final Reservation reservation;

    public CreateReservationDAO (final Connection connection, final Reservation reservation) {
        super(connection);

        if (reservation == null) {
            LOGGER.error("The reservation cannot be null.");
            throw new NullPointerException("The reservation cannot be null.");
        }
        this.reservation = reservation;
    }

    /** prepare and execute the query */
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
                        LocalDateTime.parse(rs.getString("start_time")),
                        LocalDateTime.parse(rs.getString("end_time")),
                        rs.getInt("n_people"),
                        rs.getString("user_customerID"),
                        rs.getInt("tableNumber"));

                LOGGER.info("Reservation {} successfully stored.", r.getId());
            }
        }

        outputParam = r;
    }
}
