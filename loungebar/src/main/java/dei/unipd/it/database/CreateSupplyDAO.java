package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;
import dei.unipd.it.resources.SupplyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for creating a supply item.
 *
 * <p>Saves supply name and stock quantity.</p>
 *
 * <p>Returns the created {@link Supply}.</p>
 */
public class CreateSupplyDAO extends AbstractDAO<Supply> {

    /**
     * SQL statement used to insert a new supply and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.Supply (name, units_available, type) VALUES (?, ?, ?) RETURNING *";

    /**
     * The supply data to be persisted.
     */
    private final Supply supply;

    /**
     * Creates a new DAO for inserting a specific supply.
     *
     * @param connection the database connection
     * @param supply the supply object containing the data to be stored
     * @throws NullPointerException if {@code supply} is null
     */
    public CreateSupplyDAO(final Connection connection, final Supply supply) {
        super(connection);
        if (supply == null) {
            LOGGER.error("The supply cannot be null.");
            throw new NullPointerException("The supply cannot be null.");
        }
        this.supply = supply;
    }

    /**
     * Creates a supply item in the database.
     *
     * <p>Sets name and stock quantity, executes the insert, and returns the saved {@link Supply}.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Supply u = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, supply.getName());
            preparedStatement.setInt(2, supply.getUnitsAvailable());
            preparedStatement.setObject(3, supply.getType().name(), java.sql.Types.OTHER);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                u = new Supply(
                        rs.getString("name"),
                        rs.getInt("units_available"),
                        SupplyType.valueOf(rs.getString("type"))
                );

                LOGGER.info("Supply {} successfully stored.", u.getName());
            }
        }

        outputParam = u;
    }
}