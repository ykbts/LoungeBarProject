package dei.unipd.it.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dei.unipd.it.resources.Tables;


public class CreateTableDAO extends AbstractDAO<Tables> {
    private static final String STATEMENT = "INSERT INTO lounge.Tables (number, capacity, is_available) VALUES (?, ?, ?) RETURNING *";
    private final Tables table;

    public CreateTableDAO(final Connection connection, final Tables table) {
        super(connection);
        if (table == null) {
            LOGGER.error("The table cannot be null.");
            throw new NullPointerException("The table cannot be null.");
        }
        this.table = table;
    }

    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Tables t = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setInt(1, table.getNumber());
            preparedStatement.setInt(2, table.getCapacity());
            preparedStatement.setBoolean(3, table.isAvailable());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                t = new Tables(rs.getInt("number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_available"));

                LOGGER.info("Table {} successfully stored.", t.getNumber());
            }
        }

        outputParam = t;
    
    }
}


