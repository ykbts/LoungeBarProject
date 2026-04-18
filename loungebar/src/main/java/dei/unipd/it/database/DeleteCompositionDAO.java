package dei.unipd.it.database;

import dei.unipd.it.resources.Composition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteCompositionDAO extends AbstractDAO<Composition> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Composition WHERE drink_name = ? AND supply_name = ? RETURNING *;";

    private final Composition composition;

    public DeleteCompositionDAO(final Connection con, final Composition composition) {
        super(con);

        if (composition == null) {
            LOGGER.error("The composition object cannot be null");
            throw new NullPointerException("The composition object cannot be null");
        }

        if (composition.getDrinkName() == null || composition.getDrinkName().isBlank()) {
            LOGGER.error("Invalid drink name provided for deletion");
            throw new IllegalArgumentException("Invalid drink name provided");
        }

        if (composition.getSupplyName() == null || composition.getSupplyName().isBlank()) {
            LOGGER.error("Invalid supply name provided for deletion");
            throw new IllegalArgumentException("Invalid supply name provided");
        }

        this.composition = composition;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Composition deletedComposition = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, composition.getDrinkName());
            pstmt.setString(2, composition.getSupplyName());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedComposition = new Composition(
                        rs.getString("drink_name"),
                        rs.getString("supply_name"),
                        rs.getInt("amount")
                );

                LOGGER.info("Supply {} for drink {} successfully deleted from composition", deletedComposition.getSupplyName(), deletedComposition.getDrinkName());
            } else {
                LOGGER.warn("Nothing was deleted", composition.getDrinkName(), composition.getSupplyName());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedComposition;
    }
}
