package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

/**
 * Base class for system resources with JSON serialization support.
 *
 * <p>Provides shared utilities for:
 * <ul>
 * <li>JSON factory and object mapper configuration</li>
 * <li>Standardized date-time formatting</li>
 * <li>Resource logging</li>
 * </ul>
 * </p>
 */
public abstract class AbstractResource implements Resource {

    /**
     * A LOGGER available for all the subclasses.
     */
    protected static final Logger LOGGER = LogManager.getLogger(AbstractResource.class, StringFormatterMessageFactory.INSTANCE);

    /**
     * The JSON factory to be used for creating JSON parsers and generators.
     */
    protected static final JsonFactory JSON_FACTORY;

    /**
     * The Object Mapper to serialize / deserialize JSON objects
     */
    protected static final ObjectMapper MAPPER;

    /**
     * The object that define the datetime format
     */
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    static {
        // set up the JSON factory
        JSON_FACTORY = new JsonFactory();
        JSON_FACTORY.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        JSON_FACTORY.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);

        LOGGER.debug("JSON factory successfully set up.");

        // set up the Object Mapper
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LOGGER.debug("Object Mapper successfully set up.");
    }

    /**
     * Serializes the resource to the provided output stream.
     *
     * @param out the output stream
     * @throws IOException if serialization fails or stream is null
     */
    public void toJSON(final OutputStream out) throws IOException {

        if (out == null) {
            LOGGER.error("The output stream cannot be null.");
            throw new IOException("The output stream cannot be null.");
        }

        try {
            writeJSON(out);
        }
        catch(Exception e) {
            LOGGER.error("Unable to serialize the resource to JSON.", e);
            throw new IOException("Unable to serialize the resource to JSON.", e);
        }

    }

    /**
     * Performs the actual writing of JSON.
     *
     * Subclasses have to implement this method to provide the actual logic needed for representing the {@code
     * Resource} to JSON.
     *
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     *
     * @throws Exception if something goes wrong during writing.
     */
    protected abstract void writeJSON(final OutputStream out) throws IOException;
}
