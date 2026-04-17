package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UpdateSupplyDAO extends AbstractDAO<Supply> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE lounge.Supply SET units_available = ? WHERE name = ?;";

    private final Supply supply;

    public UpdateSupplyDAO(final Connection con, final Supply supply) {
        super(con);

        if (supply == null) {
            LOGGER.error("The supply cannot be null");
            throw new NullPointerException("The supply cannot be null");
        }

        if (supply.getName() == null || supply.getName().isBlank()) {
            LOGGER.error("The name cannot be null or empty");
            throw new IllegalArgumentException("The name cannot be null or empty");
        }

        this.supply = supply;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);

            pstmt.setInt(1, supply.getUnitsAvailable());
            pstmt.setString(2, supply.getName());

            pstmt.execute();

            LOGGER.info("Supply {} successfully updated.", supply.getName());

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}