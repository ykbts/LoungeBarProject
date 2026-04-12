package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class Reservation extends AbstractResource {

    private final long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int people;
    private final String customer;
    private final int table;


    public Reservation(long id, LocalDateTime startTime, LocalDateTime endTime, int people, String customer, int table) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.people = people;
        this.customer = customer;
        this.table = table;
    }

    public long getId() {
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
    protected void writeJSON (OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("reservation");

        jg.writeStartObject();

        jg.writeNumberField("id", id);
        jg.writeStringField("startTime", MAPPER.writeValueAsString(startTime));
        jg.writeStringField("endTime", MAPPER.writeValueAsString(endTime));
        jg.writeNumberField("people", people);
        jg.writeStringField("customer", customer);
        jg.writeNumberField("table", table);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Reservation fromJSON (InputStream in) throws IOException {

        // fields read from JSON
        long jId = -1;
        LocalDateTime jStartTime = null;
        LocalDateTime jEndTime = null;
        int jPeople = -1;
        String jCustomer = null;
        int jTable = -1;

        try {
            // create parser
            final JsonParser jp = JSON_FACTORY.createParser(in);

            // advance to next element until we are to the start of the desired element
            //  or the current element is not a token element
            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"reservation".equals(jp.getCurrentName())) {

                // no more elements
                if (jp.nextToken() == null) {
                    LOGGER.error("No RESERVATION object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no Reservation object found.");
                }
            }

            // once found our object
            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getLongValue();
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
