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

    protected abstract void writeJSON(final OutputStream out) throws IOException;
}
