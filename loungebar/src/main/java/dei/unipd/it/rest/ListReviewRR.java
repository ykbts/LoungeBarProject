package dei.unipd.it.rest;

import dei.unipd.it.resources.Actions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

public final class ListReviewRR extends AbstractRR {

    public ListReviewRR (HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        super(Actions.LIST_REVIEWS, req, resp, conn);
    }

    @Override
    protected void doServe() throws IOException {

    }
}
