package dei.unipd.it.servlets.processors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public interface ResourceProcessor {

    /**
     * Attempts to process the request.
     *
     * @param req the HTTP request.
     * @param resp the HTTP response.
     * @return {@code true} if the request was recognized and handled;
     *         {@code false} if it should be passed to the next processor.
     * @throws Exception if an error occurs while handling the request.
     */
    boolean process(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception;
}
