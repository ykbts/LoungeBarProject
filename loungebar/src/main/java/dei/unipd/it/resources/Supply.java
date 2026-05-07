package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents an inventory item or ingredient in the lounge bar.
 */
public class Supply extends AbstractResource {

    private final String name;
    private final int unitsAvailable;
    private final SupplyType type;

    /**
     * @param name the unique name of the supply
     * @param unitsAvailable the current quantity in stock
     * @param type      the type of supply
     */
    public Supply(final String name, final int unitsAvailable, final SupplyType type) {
        this.name = name;
        this.unitsAvailable = unitsAvailable;
        this.type = type;
    }

    public final String getName() {
        return name;
    }

    public final int getUnitsAvailable() {
        return unitsAvailable;
    }

    public final SupplyType getType() { return type; }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("supply");
        jg.writeStartObject();

        jg.writeStringField("name", name);
        jg.writeNumberField("units-available", unitsAvailable);
        jg.writeStringField("type", type.toString());

        jg.writeEndObject();
        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Parses a Supply from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Supply instance
     * @throws IOException if parsing fails
     */
    public static Supply fromJSON(InputStream in) throws IOException {

        String jName = null;
        int jUnitsAvailable = -1;
        SupplyType jType = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"supply".equals(jp.getCurrentName())) {
                if (jp.nextToken() == null) {
                    LOGGER.error("No SUPPLY object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no SUPPLY object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "name":
                            jp.nextToken();
                            jName = jp.getText();
                            break;
                        case "units-available":
                            jp.nextToken();
                            jUnitsAvailable = jp.getIntValue();
                            break;
                        case "type":
                            jp.nextToken();
                            jType = SupplyType.valueOf(jp.getText());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a SUPPLY object from JSON.", e);
            throw e;
        }

        return new Supply(jName, jUnitsAvailable, jType);
    }
}