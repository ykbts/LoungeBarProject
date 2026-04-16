package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateSupplyDAO extends AbstractDAO<Supply> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.Supply (name, units_available) VALUES (?, ?) RETURNING *";
    private final Supply supply;

    public CreateSupplyDAO(final Connection connection, final Supply supply) {
        super(connection);
        if (supply == null) {
            LOGGER.error("The user cannot be null.");
            throw new NullPointerException("The user cannot be null.");
        }
        this.supply = supply;
    }

    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Supply u = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, supply.getName());
            preparedStatement.setInt(2, supply.getUnitsAvailable());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                u = new Supply(
                        rs.getString("name"),
                        rs.getInt("units_available")
                );

                LOGGER.info("Supply {} successfully stored.", u.getName());
            }
        }

        outputParam = u;

    }
}
