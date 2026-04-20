package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class UpdateReservationDAO extends AbstractDAO<Reservation>{

    private static final String STATEMENT = "UPDATE lounge.Reservation SET";
    private final Reservation reservation;

    /**
     * create a new DAO for updating a reservation's details
     *
     * @param conn      the connection to the database
     * @param reservation   the updated Reservation instance containing the new information
     */
    public UpdateReservationDAO(final Connection conn, final Reservation reservation) {
        super(conn);

        if (reservation == null) {
            LOGGER.error("Reservation cannot be null.");
            throw new NullPointerException("Reservation cannot be null.");
        }

        this.reservation = reservation;
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        // the updated reservation
        Reservation r = null;

        // build SET clause dynamically based on non-null fields
        List<String> clauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();


        if (reservation.getStartTime() != null) {
            clauses.add("start_time = ?");
            params.add(reservation.getStartTime());
        }
        if (reservation.getEndTime() != null) {
            clauses.add("end_time = ?");
            params.add(reservation.getEndTime());
        }
        if (reservation.getPeople() > 0) {
            clauses.add("n_people = ?");
            params.add(reservation.getPeople());
        }
        if (reservation.getTable() > 0) {
            clauses.add("tableNumber = ?");
            params.add(reservation.getTable());
        }
        if (reservation.getCustomer() != null) {
            clauses.add("user_customerID = ?");
            params.add(reservation.getCustomer());
        }

        // if no fields to update, return immediately
        if (clauses.isEmpty()) {
            LOGGER.warn("No fields to update for RESERVATION.");
            return;
        }


        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");

        // append not null clauses
        sb.append(String.join(", ", clauses));

        sb.append(" WHERE id = ? RETURNING *");
        sb.append(";");


        try {
            ps = conn.prepareStatement(sb.toString());

            // bind SET parameters
            int index = 1;
            for (Object param : params)    ps.setObject(index++, param);

            // bind WHERE parameter
            ps.setInt(index, reservation.getId());

            rs = ps.executeQuery();

//            int affectedRows = ps.executeUpdate();
//
//            if (affectedRows == 0) {
//                LOGGER.warn("No reservation found with id {}", reservation.getId());
//            } else {
//                LOGGER.info("Reservation with id {} successfully updated.", reservation.getId());
//            }

            if (rs.next()) {
                r = new Reservation(
                        rs.getInt("id"),
                        rs.getObject("start_time", LocalDateTime.class),
                        rs.getObject("end_time", LocalDateTime.class),
                        rs.getInt("n_people"),
                        rs.getString("user_customerID"),
                        rs.getInt("tableNumber")
                );

                LOGGER.info("Reservation {} successfully updated.", r.getId());
            }

        }
        catch (final Throwable t) {
            LOGGER.error("Cannot update reservation: unexpected error.", t);
        }
        finally {
            if (ps != null)     ps.close();
            if (rs != null)     rs.close();
        }

        outputParam = r;
    }
}
