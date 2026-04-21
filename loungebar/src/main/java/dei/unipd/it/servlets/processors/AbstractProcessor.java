package dei.unipd.it.servlets.processors;

import dei.unipd.it.resources.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.Connection;

public abstract class AbstractProcessor implements ResourceProcessor {

    protected static final Logger LOGGER = LogManager.getLogger(AbstractProcessor.class, StringFormatterMessageFactory.INSTANCE);
    protected static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    public boolean process(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {
        return doProcess(req, resp, conn);
    }

    /**
     * Performs the actual logic needed for serving the REST resources' processing.
     *
     * @return  {@code true} if the resource is the expected one; {@code false} otherwise.
     * @throws Exception    if any error occurs
     */
    protected abstract boolean doProcess(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception;


    /**
     *
     * @param resp      the HTTP response.
     * @param method    the method of the request
     * @param resource  the resource searched
     * @throws IOException    if any error occurs while getting the response output streamer
     */
    protected static void methodNotAllowed(
            HttpServletResponse resp,
            String method,
            String resource) throws IOException {

        LOGGER.warn("Unsupported operation for URI {}: {}.", resource, method);
        final Message m = new Message(
                String.format("Unsupported operation for URI %s", resource),
                "E4A5",
                String.format("Requested operation: %s", method));

        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.setContentType(JSON_UTF_8_MEDIA_TYPE);
        m.toJSON(resp.getOutputStream());
    }
}
