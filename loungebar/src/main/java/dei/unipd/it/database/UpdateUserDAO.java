package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UpdateUserDAO extends AbstractDAO<User> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE lounge.\"user\" SET password = ?, name = ?, surname = ?, phone = ?, role = ?::lounge.UserRole WHERE username = ?;";

    private final User user;

    public UpdateUserDAO(final Connection con, final User user) {
        super(con);

        if (user == null) {
            LOGGER.error("The user cannot be null");
            throw new NullPointerException("The user cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            LOGGER.error("The username cannot be null or empty");
            throw new IllegalArgumentException("The username cannot be null or empty");
        }

        this.user = user;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getRole().toString());
            pstmt.setString(6, user.getUsername());

            pstmt.execute();

            LOGGER.info("User {} successfully updated.", user.getUsername());
            this.outputParam = user;

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}