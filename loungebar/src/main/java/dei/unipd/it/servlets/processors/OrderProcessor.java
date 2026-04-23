package dei.unipd.it.servlets.processors;

import dei.unipd.it.rest.UpdateOrderStatusRR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

public final class OrderProcessor extends AbstractProcessor {

    // Define URL: /rest/order
    private final static String SEGMENT = "order";

    @Override
    protected boolean doProcess(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {

        final String uri = req.getRequestURI();
        // Search in the URL
        final int segmentIndex = uri.lastIndexOf("rest/" + SEGMENT);

        if (segmentIndex < 0) {
            return false;
        }

        // Extract what after the url
        final String subPath = uri.substring(segmentIndex + "rest/".length() + SEGMENT.length());

        dispatchByPath(subPath, req, resp, conn);
        return true;
    }

    private void dispatchByPath(String subPath, HttpServletRequest req, HttpServletResponse resp, Connection conn) throws IOException {

        final String method = req.getMethod();

        
        if (subPath.equals("/update-status")) {
            
            switch (method) {
                case "POST" -> new UpdateOrderStatusRR(req, resp, conn).serve();
                default     -> methodNotAllowed(resp, method, "/order/update-status");
            }
            
        } else {
            
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.warn("Unknown subpath for OrderProcessor: %s", subPath);
        }
    }
}