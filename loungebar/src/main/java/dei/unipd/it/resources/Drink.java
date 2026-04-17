package dei.unipd.it.resources;

import java.io.IOException;
import java.io.OutputStream;

public class Drink extends AbstractResource {
    private final String name;
    private final double price;
    private final boolean isAlcoholic;

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

    }
}
