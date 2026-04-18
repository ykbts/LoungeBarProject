package dei.unipd.it.database;

import dei.unipd.it.resources.Drink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UpdateDrinkDAO extends AbstractDAO<Drink> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE lounge.drink SET price = ?, is_alcoholic = ? WHERE name = ?;";

    private final Drink drink;

    public UpdateDrinkDAO(final Connection con, final Drink drink) {
        super(con);

        if (drink == null) {
            LOGGER.error("The drink cannot be null");
            throw new NullPointerException("The drink cannot be null");
        }

        if (drink.getName() == null || drink.getName().isBlank()) {
            LOGGER.error("The drink name cannot be null or empty");
            throw new IllegalArgumentException("The drink name cannot be null or empty");
        }

        this.drink = drink;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);

            pstmt.setDouble(1, drink.getPrice());
            pstmt.setBoolean(2, drink.isAlcoholic());
            pstmt.setString(3, drink.getName());

            pstmt.execute();

            LOGGER.info("Drink {} successfully updated.", drink.getName());
            this.outputParam = drink;

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}