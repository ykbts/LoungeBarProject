package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public final class ResourceList<T extends Resource> extends AbstractResource {

    private final Iterable<T> list;

    public ResourceList(final Iterable<T> list) {

        if(list == null) {
            LOGGER.error("Resource list cannot be null.");
            throw new NullPointerException("Resource list cannot be null.");
        }

        this.list = list;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("resource-list");

        jg.writeStartArray();

        jg.flush();

        boolean firstElement = true;
        for(final Resource r : list) {

            if (firstElement) {

                r.toJSON(out);
                jg.flush();
                firstElement = false;
            }
            else {
                jg.writeRaw(',');
                jg.flush();
                r.toJSON(out);
                jg.flush();
            }
        }
        jg.writeEndArray();

        jg.writeEndObject();

        jg.flush();
    }
}
