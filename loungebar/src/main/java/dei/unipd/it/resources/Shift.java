package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class Shift extends AbstractResource{

    private final int id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Shift(int id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("shift");

        jg.writeStartObject();

        jg.writeNumberField("id", id);
        jg.writeStringField("start-time", MAPPER.writeValueAsString(startTime));
        jg.writeStringField("end-time", MAPPER.writeValueAsString(endTime));

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Shift fromJSON (InputStream in) throws IOException {

        int jId = -1;
        LocalDateTime jStartTime = null;
        LocalDateTime jEndTime = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"shift".equals(jp.getCurrentName())) {

                if (jp.nextToken() == null) {
                    LOGGER.error("No SHIFT object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no SHIFT object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getIntValue();
                            break;
                        case "start-time":
                            jp.nextToken();
                            jStartTime = MAPPER.readValue(jp, LocalDateTime.class);
                            break;
                        case "end-time":
                            jp.nextToken();
                            jEndTime = MAPPER.readValue(jp, LocalDateTime.class);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a SHIFT object from JSON.", e);
            throw e;
        }

        return new Shift(jId, jStartTime, jEndTime);
    }
}
