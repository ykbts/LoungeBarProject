package dei.unipd.it.database;

import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchUserDAO extends AbstractDAO<List<User>> {

    private static final String STATEMENT = "SELECT * FROM lounge.Users ";

    private final String username;
    private final String password;
    private final String name;
    private final String surname;
    private final String phone;
    private final String role;


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

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<User> users = new ArrayList<User>();
        final List<String> clauses= new ArrayList<String>();


        if(username != null ){ clauses.add(" username = ? "); }
        if(password != null ){ clauses.add(" password = ? "); }
        if(name != null ){ clauses.add(" name = ? "); }
        if(surname != null ){ clauses.add(" surname = ? "); }
        if(phone != null ){ clauses.add(" phone = ? "); }
        if(role != null ){ clauses.add(" role = ? "); }


        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");
        if(clauses.size()>0){
            sb.append(" WHERE ");
            boolean first= true;
            for(String s : clauses){
                if(first){
                    first=false;
                    sb.append(s);
                }else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");



        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;


            if(username!=null){
                pstmt.setString(clauseCount++, username);
            }
            if(password!=null){
                pstmt.setString(clauseCount++, password);
            }
            if(name!=null) {
                pstmt.setString(clauseCount++, name);
            }
            if(surname!=null) {
                pstmt.setString(clauseCount++, surname);
            }
            if(phone!=null) {
                pstmt.setString(clauseCount++, phone);
            }
            if(role!=null) {
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
