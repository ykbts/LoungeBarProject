package dei.unipd.it.database;

import dei.unipd.it.resources.Supply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteSupplyDAO extends AbstractDAO<Supply> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Supply WHERE name = ? RETURNING *;";

    private final Supply supply;

    public DeleteSupplyDAO(final Connection con, final Supply supply) {
        super(con);

        if (supply == null) {
            LOGGER.error("The supply cannot be null");
            throw new NullPointerException("The supply cannot be null");
        }

        if (supply.getName() == null || supply.getName().isBlank()) {
            LOGGER.error("Invalid supply name provided for deletion");
            throw new IllegalArgumentException("Invalid supply name provided");
        }

        this.supply = supply;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Supply deletedSupply = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, supply.getName());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedSupply = new Supply(
                        rs.getString("name"),
                        rs.getInt("units_available")
                );

                LOGGER.info("Supply {} successfully deleted.", deletedSupply.getName());
            } else {
                LOGGER.warn("Nothing was deleted", supply.getName());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedSupply;
    }
}