package dei.unipd.it.resources;

public class OrderItem {

    private final String drinkName;
    private final long orderID;
    private final int quantity;

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
