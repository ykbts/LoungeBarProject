package dei.unipd.it.resources;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a beverage in the catalog.
 *
 * <p>Contains details regarding the drink's name, price, and alcoholic status.</p>
 */
public class Drink extends AbstractResource {
    private final String name;
    private final double price;
    private final boolean isAlcoholic;

    /**
     * @param name the name of the drink
     * @param price the selling price
     * @param isAlcoholic true if the drink contains alcohol
     */
    public Drink(final String name, final double price, final boolean isAlcoholic) {
        this.name = name;
        this.price = price;
        this.isAlcoholic = isAlcoholic;
    }

    public final String getName() {
        return name;
    }

    public final double getPrice() {
        return price;
    }

    public final boolean isAlcoholic() {
        return isAlcoholic;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {
        MAPPER.writeValue(out, this);
    }
}