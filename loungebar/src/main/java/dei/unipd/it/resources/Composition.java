package dei.unipd.it.resources;

/**
 * Represents the ingredients and quantities required for a drink.
 *
 * <p>Defines the recipe mapping between a {@code Drink} and a {@code Supply}.</p>
 */
public class Composition {

    /** The name of the drink. */
    private final String drinkName;

    /** The name of the ingredient. */
    private final String supplyName;

    /** The quantity required. */
    private final int amount;

    /**
     * @param drinkName name of the drink
     * @param supplyName name of the supply
     * @param amount required quantity
     */
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