package dei.unipd.it.database;

import dei.unipd.it.resources.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteDrinkDAO extends AbstractDAO<Drink> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Drink WHERE name = ? RETURNING *;";

    private final String drinkName;

    public DeleteDrinkDAO(final Connection con, final String drinkName) {
        super(con);

        if (drinkName == null) {
            LOGGER.error("The drink object cannot be null");
            throw new NullPointerException("The drink object cannot be null");
        }

        this.drinkName = drinkName;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Drink deletedDrink = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, drinkName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedDrink = new Drink(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_alcoholic")
                );

                LOGGER.info("Drink successfully deleted", deletedDrink.getName());
            } else {
                LOGGER.warn("Nothing was deleted", drinkName);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedDrink;
    }
}