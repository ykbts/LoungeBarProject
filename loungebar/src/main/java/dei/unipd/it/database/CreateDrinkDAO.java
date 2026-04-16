package dei.unipd.it.database;

import dei.unipd.it.resources.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateDrinkDAO extends AbstractDAO<Drink> {

    private static final String STATEMENT = "INSERT INTO lounge.Drink (name, price, is_alcoholic) VALUES (?, ?, ?) RETURNING *";
    private final Drink drink;

    public CreateDrinkDAO (final Connection connection, final Drink drink) {
        super(connection);

        if (drink == null) {
            LOGGER.error("The drink cannot be null.");
            throw new NullPointerException("The drink cannot be null.");
        }
        this.drink = drink;
    }

    /** prepare and execute the query */
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Drink r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, drink.getName());
            preparedStatement.setDouble(2, drink.getPrice());
            preparedStatement.setBoolean(3, drink.isAlcoholic());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Drink (
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_alcoholic"));

                LOGGER.info("Drink {} successfully stored.", r.getName());
            }
        }

        outputParam = r;
    }
}