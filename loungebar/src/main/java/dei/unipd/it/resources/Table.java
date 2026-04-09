package dei.unipd.it.resources;

public class Table {
    /**
     * The unique number identifying the table
     */
    private final int number;

    /**
     * The maximum seating capacity of the table
     */
    private final int capacity;

    /**
     * The availability status of the table
     */
    private final boolean isAvailable;

    /**
     * Creates a new Table.
     * @param number the identifying number of the table.
     * @param capacity the seating capacity of the table.
     * @param isAvailable the availability status of the table.
     */
    public Table(final int number, final int capacity, final boolean isAvailable) {
        this.number = number;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
    }

    /**
     * Returns the identifying number of the table.
     * * @return the identifying number of the table.
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Returns the seating capacity of the table.
     * * @return the seating capacity of the table.
     */
    public final int getCapacity() {
        return capacity;
    }

    /**
     * Returns the availability status of the table.
     * * @return {@code true} if the table is available; {@code false} otherwise.
     */
    public final boolean isAvailable() {
        return isAvailable;
    }
}

