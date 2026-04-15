package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateReservationDAO extends AbstractDAO<Reservation> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Reservation (startTime, endTime, people, customer, table) VALUES (?, ?, ?, ?, ?) RETURNING *";
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

            preparedStatement.setString(1, reservation.getStartTime().toString());
            preparedStatement.setString(2, reservation.getEndTime().toString());
            preparedStatement.setInt(3, reservation.getPeople());
            preparedStatement.setString(4, reservation.getCustomer());
            preparedStatement.setInt(5, reservation.getTable());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Reservation (rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("startTime")),
                        LocalDateTime.parse(rs.getString("endTime")),
                        rs.getInt("people"),
                        rs.getString("customer"),
                        rs.getInt("table"));

                LOGGER.info("Reservation {} successfully stored.", r.getId());
            }
        }

        outputParam = r;
    }
}
