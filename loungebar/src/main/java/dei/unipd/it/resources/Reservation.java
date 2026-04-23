package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * Represents a table reservation in the lounge bar.
 */
public class Reservation extends AbstractResource {

    private final int id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int people;
    private final String customer;
    private final int table;

    /**
     * @param id unique reservation identifier
     * @param startTime start of the booking
     * @param endTime end of the booking
     * @param people number of guests
     * @param customer customer identifier
     * @param table assigned table number
     */
    public Reservation(int id, LocalDateTime startTime, LocalDateTime endTime, int people, String customer, int table) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.people = people;
        this.customer = customer;
        this.table = table;
    }

    public int getId() {
        return id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getPeople() {
        return people;
    }
    public String getCustomer() {
        return customer;
    }
    public int getTable() {
        return table;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("reservation");
        jg.writeStartObject();
        jg.writeNumberField("id", id);
        jg.writeStringField("startTime", startTime.format(FORMATTER));
        jg.writeStringField("endTime", endTime.format(FORMATTER));
        jg.writeNumberField("people", people);
        jg.writeStringField("customer", customer);
        jg.writeNumberField("table", table);
        jg.writeEndObject();
        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Parses a Reservation from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Reservation instance
     * @throws IOException if parsing fails
     */
    public static Reservation fromJSON(InputStream in) throws IOException {
        int jId = -1, jPeople = -1, jTable = -1;
        LocalDateTime jStartTime = null, jEndTime = null;
        String jCustomer = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"reservation".equals(jp.getCurrentName())) {

                // no more elements
                if (jp.nextToken() == null) {
                    LOGGER.error("No RESERVATION object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no RESERVATION object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getIntValue();
                            break;
                        case "startTime":
                            jp.nextToken();
                            jStartTime = MAPPER.readValue(jp, LocalDateTime.class);
                            break;
                        case "endTime":
                            jp.nextToken();
                            jEndTime = MAPPER.readValue(jp, LocalDateTime.class);
                            break;
                        case "people":
                            jp.nextToken();
                            jPeople = jp.getIntValue();
                            break;
                        case "customer":
                            jp.nextToken();
                            jCustomer = jp.getText();
                            break;
                        case "table":
                            jp.nextToken();
                            jTable = jp.getIntValue();
                            break;
                    }
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("Unable to parse a RESERVATION object from JSON.", e);
            throw e;
        }

        return new Reservation(jId, jStartTime, jEndTime, jPeople, jCustomer, jTable);
    }
}