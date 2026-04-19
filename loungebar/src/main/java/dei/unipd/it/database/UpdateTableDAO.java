package dei.unipd.it.database;

import dei.unipd.it.resources.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class UpdateTableDAO extends AbstractDAO<Tables> {

    private static final String STATEMENT = "UPDATE lounge.Tables SET";
    private final Tables table;

    /**
     * create a new DAO
     *
     * @param conn      the connection to the database
     * @param table   the updated Reservation instance containing the new information
     */
    public UpdateTableDAO(final Connection conn, final Tables table) {
        super(conn);

        if (table == null) {
            LOGGER.error("Table cannot be null.");
            throw new NullPointerException("Table cannot be null.");
        }

        this.table = table;
    }

    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        // the updated reservation
        Tables t = null;

        // build SET clause dynamically based on non-null fields
        List<String> clauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();


        if (table.getCapacity() != null) {
            clauses.add("capacity = ?");
            params.add(table.getCapacity());
        }

        if (table.isAvailable() != null) {
            clauses.add("is_available = ?");
            params.add(table.isAvailable());
        }

        // if no fields to update, return immediately
        if (clauses.isEmpty()) {
            LOGGER.warn("No fields to update for TABLE {}.", table.getNumber());
            return;
        }


        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");

        // append not null clauses
        sb.append(String.join(", ", clauses));

        sb.append(" WHERE number = ? RETURNING *");
        sb.append(";");


        try {
            ps = conn.prepareStatement(sb.toString());

            // bind SET parameters
            int index = 1;
            for (Object param : params)    ps.setObject(index++, param);

            // bind WHERE parameter
            ps.setInt(index, table.getNumber());

            rs = ps.executeQuery();

            if (rs.next()) {
                t = new Tables(
                        rs.getInt("number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_available")
                );

                LOGGER.info("Table {} successfully updated.", t.getNumber());
            }
        }
        catch (SQLException e) {
            LOGGER.error("Cannot update reservation: unexpected error.", e);
        }
        finally {
            if (ps != null)     ps.close();
            if (rs != null)     rs.close();
        }

        outputParam = t;
    }
}
