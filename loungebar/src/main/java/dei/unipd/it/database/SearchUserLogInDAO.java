package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class SearchUserLogInDAO extends AbstractDAO<User> {

    private static final String STATEMENT = "SELECT * FROM lounge.User WHERE username = ? AND password = ?;";

    private final String username;
    private final String password;

    public SearchUserLogInDAO(final Connection con, final String username, final String password) {
        super(con);
        this.username = username;
        this.password = password;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        null,
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("role"))
                );

                LOGGER.info("User {} successfully authenticated", username);
            } else {
                LOGGER.warn("Authentication failed for user {}", username);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        this.outputParam = user;
    }
}