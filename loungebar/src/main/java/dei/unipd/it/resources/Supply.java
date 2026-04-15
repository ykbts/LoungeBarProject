package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Supply extends AbstractResource {

    private final String name;
    private final int unitsAvailable;

    public Supply(final String name, final int unitsAvailable) {
        this.name = name;
        this.unitsAvailable = unitsAvailable;
    }

    public final String getName() {
        return name;
    }

    public final int getUnitsAvailable() {
        return unitsAvailable;
    }


    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("supply");

        jg.writeStartObject();

        jg.writeStringField("name", name);
        jg.writeNumberField("units-available", unitsAvailable);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Supply fromJSON (InputStream in) throws IOException {

        String jName = null;
        int jUnitsAvailable = -1;

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

                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a SUPPLY object from JSON.", e);
            throw e;
        }

        return new Supply(jName, jUnitsAvailable);
    }
}