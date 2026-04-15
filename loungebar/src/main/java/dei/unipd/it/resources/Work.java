package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Work extends AbstractResource {

    private final String waiterID;
    private final int shiftID;

    public Work (String waiterID, int shiftID) {
        this.waiterID = waiterID;
        this.shiftID = shiftID;
    }
    public String getWaiterID() {
        return waiterID;
    }
    public int getShiftID() {
        return shiftID;
    }


    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("work");

        jg.writeStartObject();

        jg.writeStringField("waiter-id", waiterID);
        jg.writeNumberField("shift-id", shiftID);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Work fromJSON(InputStream in) throws IOException {

        String jWaiter = null;
        int jShift = -1;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"work".equals(jp.getCurrentName())) {

                if (jp.nextToken() == null) {
                    LOGGER.error("No WORK object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no WORK object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "waiter-id":
                            jp.nextToken();
                            jWaiter = jp.getText();
                            break;
                        case "shift-id":
                            jp.nextToken();
                            jShift = jp.getIntValue();
                            break;

                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a WORK object from JSON.", e);
            throw e;
        }

        return new Work(jWaiter, jShift);
    }
}
