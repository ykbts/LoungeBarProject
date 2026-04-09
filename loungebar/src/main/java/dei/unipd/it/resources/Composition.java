package dei.unipd.it.resources;

public class Composition {

    private final String drinkName;
    private final String supplyName;
    private final int amount;

    public Composition(String drinkName, String supplyName, int amount) {
        this.drinkName = drinkName;
        this.supplyName = supplyName;
        this.amount = amount;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getAmount() {
        return amount;
    }
}
