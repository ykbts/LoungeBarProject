package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Tables extends AbstractResource {
    /**
     * The unique number identifying the table
     */
    private final int number;

    /**
     * The maximum seating capacity of the table
     */
    private final Integer capacity;

    /**
     * The availability status of the table
     */
    private final Boolean isAvailable;

    /**
     * Creates a new Table.
     * @param number the identifying number of the table.
     * @param capacity the seating capacity of the table.
     * @param isAvailable the availability status of the table.
     */
    public Tables(final int number, final Integer capacity, final Boolean isAvailable) {
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
    public final Integer getCapacity() {
        return capacity;
    }

    /**
     * Returns the availability status of the table.
     * * @return {@code true} if the table is available; {@code false} otherwise.
     */
    public final Boolean isAvailable() {
        return isAvailable;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("table");

        jg.writeStartObject();

        jg.writeNumberField("number", number);
        jg.writeNumberField("capacity", capacity);
        jg.writeBooleanField("is-available", isAvailable);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Tables fromJSON (InputStream in) throws IOException {

        int jNumber = -1;
        Integer jCapacity = null;
        Boolean jIsAvailable = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"table".equals(jp.getCurrentName())) {

                if (jp.nextToken() == null) {
                    LOGGER.error("No TABLE object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no TABLE object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "number":
                            jp.nextToken();
                            jNumber = jp.getIntValue();
                            break;
                        case "capacity":
                            jp.nextToken();
                            jCapacity = jp.getIntValue();
                            break;
                        case "is-available":
                            jp.nextToken();
                            jIsAvailable = jp.getBooleanValue();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a TABLE object from JSON.", e);
            throw e;
        }

        return new Tables(jNumber, jCapacity, jIsAvailable);
    }
}

