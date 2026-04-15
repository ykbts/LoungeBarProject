package dei.unipd.it.resources;

public class User {
    private final String username;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private UserRole role;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String name, String surname, String phone, UserRole role) {
        this.username = username;
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