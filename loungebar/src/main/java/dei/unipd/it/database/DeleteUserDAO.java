package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteUserDAO extends AbstractDAO<User> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.\"user\" WHERE username = ? RETURNING *;";

    private final User user;

    public DeleteUserDAO(final Connection con, final User user) {
        super(con);

        if (user == null) {
            LOGGER.error("The user cannot be null");
            throw new NullPointerException("The user cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            LOGGER.error("Invalid username provided for deletion");
            throw new IllegalArgumentException("Invalid username provided");
        }

        this.user = user;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User deletedUser = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, user.getUsername());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedUser = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("role"))
                );

                LOGGER.info("User {} successfully deleted.", deletedUser.getUsername());
            } else {
                LOGGER.warn("Nothing was deleted", user.getUsername());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedUser;
    }
}