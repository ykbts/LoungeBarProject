package dei.unipd.it.database;

import dei.unipd.it.resources.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateUserDAO extends AbstractDAO<User> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO ... (username, name, surname, phone, role) VALUES (?, ?, ?, ?, ?) RETURNING *";
    private final User user;

    public CreateUserDAO(final Connection connection, final User user) {
        super(connection);
        if (user == null) {
            LOGGER.error("The user cannot be null.");
            throw new NullPointerException("The user cannot be null.");
        }
        this.user = user;
    }

    protected final void doAccess() throws SQLException {

        ResultSet rs;
        User r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, user.getUsername().toString());
            preparedStatement.setString(2, user.getName().toString());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getRole());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new User(rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        rs.getString("role"));

                LOGGER.info("User {} successfully stored.", r.getUsername());
            }
        }

        outputParam = r;
    
    }
}
