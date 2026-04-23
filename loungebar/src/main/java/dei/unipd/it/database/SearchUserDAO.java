package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for searching users.
 *
 * <p>Filters users by credentials, personal data, contact info or role.</p>
 *
 * <p>Returns a list of matching {@link User} objects.</p>
 */
public final class SearchUserDAO extends AbstractDAO<List<User>> {

    /**
     * The base SQL statement used to retrieve user records.
     */
    private static final String STATEMENT = "SELECT * FROM lounge.User ";

    /** The username filter for the search. */
    private final String username;
    /** The password filter for the search. */
    private final String password;
    /** The name filter for the search. */
    private final String name;
    /** The surname filter for the search. */
    private final String surname;
    /** The phone filter for the search. */
    private final String phone;
    /** The role filter for the search. */
    private final String role;

    /**
     * Creates a new DAO for searching users with optional filters.
     *
     * @param con the database connection
     * @param username the username to filter by
     * @param password the password to filter by
     * @param name the first name to filter by
     * @param surname the last name to filter by
     * @param phone the phone number to filter by
     * @param role the user role to filter by
     */
    public SearchUserDAO(final Connection con, final String username, final String password,
                         final String name, final String surname, final String phone, final String role) {
        super(con);
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.role = role;
    }

    /**
     * Searches users in the database.
     *
     * <p>Executes the query, and returns matching {@link User} objects.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<User> users = new ArrayList<User>();
        final List<String> clauses = new ArrayList<String>();

        // Build dynamic clauses for non-empty parameters
        if (username != null && !username.isEmpty()) { clauses.add(" username = ? "); }
        if (password != null && !password.isEmpty()) { clauses.add(" password = ? "); }
        if (name != null && !name.isEmpty()) { clauses.add(" name = ? "); }
        if (surname != null && !surname.isEmpty()) { clauses.add(" surname = ? "); }
        if (phone != null && !phone.isEmpty()) { clauses.add(" phone = ? "); }
        if (role != null && !role.isEmpty()) { clauses.add(" role::text = ? "); }

        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");
        if(clauses.size() > 0){
            sb.append(" WHERE ");
            boolean first = true;
            for(String s : clauses){
                if(first){
                    first = false;
                    sb.append(s);
                } else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");

        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;

            if(username != null && !username.isEmpty()){
                pstmt.setString(clauseCount++, username);
            }
            if(password != null && !password.isEmpty()){
                pstmt.setString(clauseCount++, password);
            }
            if(name != null && !name.isEmpty()) {
                pstmt.setString(clauseCount++, name);
            }
            if(surname != null && !surname.isEmpty()) {
                pstmt.setString(clauseCount++, surname);
            }
            if(phone != null && !phone.isEmpty()) {
                pstmt.setString(clauseCount++, phone);
            }
            if(role != null && !role.isEmpty()) {
                pstmt.setString(clauseCount++, role);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("role"))));
            }

            LOGGER.info("User(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = users;
    }
}