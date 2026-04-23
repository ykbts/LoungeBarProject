package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for registering a new user.
 *
 * <p>Saves user credentials, personal data, contact info, and role.</p>
 *
 * <p>Returns the created {@link User}</p>
 */
public class CreateUserDAO extends AbstractDAO<User> {

    /** * SQL statement used to insert a new user and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.User (username, password, name, surname, phone, role) VALUES (?, ?, ?, ?, ?, ?::lounge.UserRole) RETURNING *";

    /**
     * The user data to be persisted.
     */
    private final User user;

    /**
     * Creates a new DAO for inserting a specific user.
     *
     * @param connection the database connection
     * @param user the user object containing the data to be stored
     * @throws NullPointerException if {@code user} is null
     */
    public CreateUserDAO(final Connection connection, final User user) {
        super(connection);
        if (user == null) {
            LOGGER.error("The user cannot be null.");
            throw new NullPointerException("The user cannot be null.");
        }
        this.user = user;
    }

    /**
     * Executes the user creation operation.
     *
     * <p>The method:
     * <ul>
     * <li>Binds user details and credentials to the SQL statement</li>
     * <li>Executes the insertion using {@code RETURNING *}</li>
     * <li>Maps the resulting {@code ResultSet} to a new {@link User} instance,
     * confirming the registration</li>
     * </ul>
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        User u = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            // Bind parameters for the new user account
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