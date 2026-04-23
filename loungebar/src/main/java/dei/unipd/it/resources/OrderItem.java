package dei.unipd.it.resources;

/**
 * Represents a specific drink item included in a customer order.
 *
 * <p>Links a {@code Drink} to an {@code Order}.</p>
 */
public class OrderItem {

    /** The name of the drink ordered. */
    private final String drinkName;

    /** The unique identifier of the parent order. */
    private final long orderID;

    /** The number of units ordered. */
    private final int quantity;

    /**
     * @param drinkName the name of the drink
     * @param orderID  the ID of the associated order
     * @param quantity the quantity of the drink
     */
    public OrderItem(String drinkName, long orderID, int quantity) {
        this.drinkName = drinkName;
        this.orderID = orderID;
        this.quantity = quantity;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public long getOrderID() {
        return orderID;
    }

    public int getQuantity() {
        return quantity;
    }
}