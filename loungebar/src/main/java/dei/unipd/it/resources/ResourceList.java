package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a list of {@link AbstractResource} objects.
 * @param <T>
 *            the type of the actual class extending {@code AbstractResource}.
 */
public final class ResourceList<T extends Resource> extends AbstractResource {

    /**
     * The list of {@code AbstractResource}s.
     */
    private final Iterable<T> list;

    /**
     * Creates a list of {@code AbstractResource}s.
     *
     * @param list the list of {@code AbstractResource}.
     */
    public ResourceList(final Iterable<T> list) {

        if(list == null) {
            LOGGER.error("Resource list cannot be null.");
            throw new NullPointerException("Resource list cannot be null.");
        }

        this.list = list;
    }

    /**
     * Write in the response's OutputStream the list of resources in JSON.
     *
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     *
     * @throws IOException if something's wrong in the output stream.
     */
    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("resourceList");

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
