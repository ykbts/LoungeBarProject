package dei.unipd.it.resources;

public class Waiter {
    /**
     * The name of the Waiter
     */
    private final String name;

    /**
     * The surname of the Waiter
     */
    private final String surname;

    /**
     * The phone number of the Waiter (used as a unique identifier or contact)
     */
    private final String phoneNumber;

    /**
     * Creates a new Waiter.
     * @param name the name of the waiter.
     * @param surname the surname of the waiter.
     * @param phoneNumber the phone number of the waiter.
     */
    public Waiter(final String name, final String surname, final String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the name of the waiter.
     * * @return the name of the waiter.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the surname of the waiter.
     * * @return the surname of the waiter.
     */
    public final String getSurname() {
        return surname;
    }

    /**
     * Returns the phone number of the waiter.
     * * @return the phone number of the waiter.
     */
    public final String getPhoneNumber() {
        return phoneNumber;
    }

        /**
     * Returns {@code true} if the {@code Waiter} has a phone number specified;
     * {@code false} otherwise.
     *
     * @return {@code true} if the {@code Waiter} has a phone number specified;
     * {@code false} otherwise.
     */
    public final boolean hasPhoneNumber() {
        return phoneNumber != null && !phoneNumber.isBlank();
    }
}

