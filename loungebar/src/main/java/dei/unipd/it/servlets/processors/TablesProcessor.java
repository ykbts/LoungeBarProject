package dei.unipd.it.servlets.processors;

import dei.unipd.it.rest.ListTablesRR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

public final class TablesProcessor extends AbstractProcessor {

    private final static String SEGMENT = "tables";

    @Override
    protected boolean doProcess(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {

        final String uri = req.getRequestURI();
        final int segmentIndex = uri.lastIndexOf("rest/" + SEGMENT);

        // if the uri doesn't match, stop immediately
        if (segmentIndex < 0)
            return false;

        // strip the prefix to extract following parameters
        final String subPath = uri.substring(segmentIndex + "rest/".length() + SEGMENT.length());

        // "sbologna" the dispatching to a dedicated function
        dispatchByPath(subPath, req, resp, conn);

        return true;
    }

    private void dispatchByPath(String subPath, HttpServletRequest req, HttpServletResponse resp, Connection conn) throws IOException {

        final String method = req.getMethod();

        if (subPath.isBlank() || subPath.equals("/")) {
            // /tables

            switch (method) {
                case "GET" -> new ListTablesRR(req, resp, conn).serve();
                default     -> methodNotAllowed(resp, method, "/tables");
            }

        }
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.warn("Unknown subpath for OrderProcessor: %s", subPath);
        }
    }
}
