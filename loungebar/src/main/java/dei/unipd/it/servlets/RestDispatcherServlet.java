package dei.unipd.it.servlets;

import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;
import dei.unipd.it.servlets.processors.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * Dispatches incoming REST requests to the appropriate resource processor.
 *
 * <p>New resource types can be supported by implementing a {@link ResourceProcessor}
 * and registering an instance in {@link #buildProcessors()}.
 */
public class RestDispatcherServlet extends AbstractDatabaseServlet {

    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";
    /// list of processors tried in sequence until one matches
    private final List<ResourceProcessor> processors = buildProcessors();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = resp.getOutputStream();

        try {

            // iterate over the registered processors
            for (ResourceProcessor processor : processors) {
                LOGGER.info("{} called.", processor.getClass().getSimpleName());

                if (processor.process(req, resp, getConnection()))
                    return;
            }


            LOGGER.warn("Unknown resource requested: {}", req.getRequestURI());
            final Message m = new Message(
                    "Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));

            // set response's STATUS and CONTENT-TYPE  and return the JSON message
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);

        }
        catch (Throwable t) {

            LOGGER.error("Unexpected error while processing the REST resource.", t);
            final Message m = new Message("Unexpected error.", "E5A1", t.getMessage());

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);
        }
        finally {
            if (out != null) {
                out.flush();
                out.close();
            }

            LogContext.removeIPAddress();
        }
    }

    /**
     * Registers all known resource processors
     * */
    private List<ResourceProcessor> buildProcessors() {

        return List.of(
                new ReviewProcessor(),
                new ReservationProcessor(),
                new OrderProcessor(),
                new OrderItemsProcessor(),
                new TablesProcessor()
        );
    }
}
