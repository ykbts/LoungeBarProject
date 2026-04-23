package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class SearchUserLogInDAO extends AbstractDAO<User> {

    private static final String QUERY = "SELECT * FROM lounge.User WHERE username = ? AND password = ? AND role = ?::lounge.userrole";

    private final String username;
    private final String password;
    private final String role;

    public SearchUserLogInDAO(final Connection con, final String username, final String password, final String role) {
        super(con);
        this.username = username;
        this.password = password;
        this.role = role;

    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            pstmt = conn.prepareStatement(QUERY);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        null,
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("role").toUpperCase())
                );

                LOGGER.info("User {} successfully authenticated", username, role);
            } else {
                LOGGER.warn("Authentication failed for user {} with role {}", username, role);
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