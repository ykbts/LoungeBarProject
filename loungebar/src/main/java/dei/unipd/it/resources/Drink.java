package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a beverage in the catalog.
 *
 * <p>Contains details regarding the drink's name, price, and alcoholic status.</p>
 */
public class Drink extends AbstractResource {
    private final String name;
    private final Double price;
    private final Boolean isAlcoholic;

    /**
     * @param name the name of the drink
     * @param price the selling price
     * @param isAlcoholic true if the drink contains alcohol
     */
    public Drink(final String name, final Double price, final Boolean isAlcoholic) {
        this.name = name;
        this.price = price;
        this.isAlcoholic = isAlcoholic;
    }

    public final String getName() {
        return name;
    }

    public final Double getPrice() {
        return price;
    }

    public final Boolean isAlcoholic() {
        return isAlcoholic;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("drink");
        jg.writeStartObject();

        jg.writeStringField("name", name);
        jg.writeNumberField("price", price);
        jg.writeBooleanField("isAlcoholic", isAlcoholic);

        jg.writeEndObject();
        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Parses a Drink from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Drink instance
     * @throws IOException if parsing fails
     */
    public static Drink fromJSON(InputStream in) throws IOException {

        String jName = null;
        Double jPrice = null;
        Boolean jIsAlcoholic = null;


        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"drink".equals(jp.getCurrentName())) {
                if (jp.nextToken() == null) {
                    LOGGER.error("No DRINK object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no DRINK object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "name":
                            jp.nextToken();
                            jName = jp.getText();
                            break;
                        case "price":
                            jp.nextToken();
                            jPrice = jp.getDoubleValue();
                            break;
                        case "isAlcoholic":
                            jp.nextToken();
                            jIsAlcoholic = jp.getBooleanValue();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a DRINK object from JSON.", e);
            throw e;
        }

        return new Drink(jName, jPrice, jIsAlcoholic);
    }
}