package dei.unipd.it.resources;

public class Customer {

    private final String phone;
    private final String name;
    private final String surname;

    public Customer(String phone, String name, String surname) {
        this.phone = phone;
        this.name = name;
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
