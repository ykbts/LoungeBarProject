package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateUserDAO extends AbstractDAO<User> {

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO lounge.User (username, password, name, surname, phone, role) VALUES (?, ?, ?, ?, ?, ?::lounge.UserRole) RETURNING *";
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
        User u = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getRole());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                u = new User(rs.getString("username"),
                        null,
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("role")));

                LOGGER.info("User {} successfully stored.", u.getUsername());
            }
        }

        outputParam = u;
    
    }
}
