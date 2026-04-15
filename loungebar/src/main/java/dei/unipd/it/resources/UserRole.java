package dei.unipd.it.resources;

public enum UserRole {
    WAITER,
    CUSTOMER,
    ADMIN;

    /**
     * Converts a string value to the corresponding UserRole enum constant.
     *
     * @param value the string representation of the role
     * @return the matching UserRole
     * @throws IllegalArgumentException if no matching role is found
     */
    public static UserRole fromString(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown UserRole: " + value);
    }

}
