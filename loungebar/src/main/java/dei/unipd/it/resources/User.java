package dei.unipd.it.resources;

/**
 * Represents a registered user of the lounge bar system.
 *
 */
public class User {
    private final String username;
    private final String password;
    private String name;
    private String surname;
    private String phone;
    private UserRole role;

    /**
     * Minimal constructor for authentication purposes.
     *
     * @param username the user's login identifier
     * @param password the user's credentials
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Complete constructor for user profile management.
     *
     * @param username the user's login identifier
     * @param password the user's credentials
     * @param name the user's first name
     * @param surname the user's last name
     * @param phone the user's contact number
     * @param role the assigned {@link UserRole}
     */
    public User(String username, String password, String name, String surname, String phone, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPhone() { return phone; }
    public String getRole() { return role.toString(); }
    public String getPassword() { return password; }

}