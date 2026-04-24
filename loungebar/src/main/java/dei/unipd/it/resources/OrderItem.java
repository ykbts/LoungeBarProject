package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a specific drink item included in a customer order.
 *
 * <p>Links a {@code Drink} to an {@code Order}.</p>
 */
public class OrderItem extends AbstractResource {

    /** The name of the drink ordered. */
    private final String drinkName;

    /** The unique identifier of the parent order. */
    private final Integer orderID;

    /** The number of units ordered. */
    private final Integer quantity;

    /**
     * @param drinkName the name of the drink
     * @param orderID  the ID of the associated order
     * @param quantity the quantity of the drink
     */
    public OrderItem(String drinkName, Integer orderID, Integer quantity) {
        this.drinkName = drinkName;
        this.orderID = orderID;
        this.quantity = quantity;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("orderItem");
        jg.writeStartObject();

        jg.writeStringField("drinkName", drinkName);
        jg.writeNumberField("orderID", orderID);
        jg.writeNumberField("quantity", quantity);

        jg.writeEndObject();
        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Parses a OrderItem from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Supply instance
     * @throws IOException if parsing fails
     */
    public static OrderItem fromJSON(InputStream in) throws IOException {

        String jName = null;
        Integer jOrderID = null;
        Integer jQuantity = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"orderItem".equals(jp.getCurrentName())) {
                if (jp.nextToken() == null) {
                    LOGGER.error("No ORDERITEM object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no ORDERITEM object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "drinkName":
                            jp.nextToken();
                            jName = jp.getText();
                            break;
                        case "orderID":
                            jp.nextToken();
                            jOrderID = jp.getIntValue();
                            break;
                        case "quantity":
                            jp.nextToken();
                            jQuantity = jp.getIntValue();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a ORDERITEM object from JSON.", e);
            throw e;
        }

        return new OrderItem(jName, jOrderID,jQuantity);
    }
}