package dei.unipd.it.servlets.processors;

import dei.unipd.it.rest.CreateReviewRR;
import dei.unipd.it.rest.ListReviewRR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public final class ReviewProcessor extends AbstractProcessor {

    private final static String SEGMENT = "review";


    @Override
    public boolean doProcess(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {

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

    /**
     *
     * @param subPath
     * @param req       the HTTP request.
     * @param resp      the HTTP response.
     * @param conn      the connection to the database
     * @throws Exception    if any error occurs.
     *
     * <p> Process the {@code subPath} of the uri to dispatch towards the right REST action </p>
     */
    private void dispatchByPath(String subPath, HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {

        final String method = req.getMethod();

        if (subPath.isBlank() || subPath.equals("/")) {
            ///     /review

            switch (method) {
                case "GET"  -> new ListReviewRR(req, resp, conn).serve();
                case "POST" -> new CreateReviewRR(req, resp, conn).serve();
                default     -> methodNotAllowed(resp, method, "/review");

            }
        }

    }

}
