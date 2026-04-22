package dei.unipd.it.servlets.processors;

import dei.unipd.it.rest.CreateReservationRR;
import dei.unipd.it.rest.CreateReviewRR;
import dei.unipd.it.rest.ListReservationRR;
import dei.unipd.it.rest.ListReviewRR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

public final class ReservationProcessor extends AbstractProcessor {

    private final static String SEGMENT = "reservation";

    // secondo me sto pezzo di codice si può mettere nell'abstract, e quindi dovrei solo implementare dispatch...()
    // devo vedere se SEGMENT si può impostare protected abstract e definire qui
    @Override
    protected boolean doProcess(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws Exception {
        LOGGER.info("¡HOLA! Entrando al Processor. URI: " + req.getRequestURI());
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
            ///     /reservation

            switch (method) {
                case "GET"  -> new ListReservationRR(req, resp, conn).serve();
                case "POST" -> new CreateReservationRR(req, resp, conn).serve();
                default     -> methodNotAllowed(resp, method, "/reservation");

            }
        }

    }


}
