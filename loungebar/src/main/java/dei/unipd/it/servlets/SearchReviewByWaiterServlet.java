package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchReviewDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Review;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet for searching reviews of a specific waiter.
 */
@WebServlet(name = "SearchReviewByWaiterServlet", value = "/review/search-by-waiter")
public final class SearchReviewByWaiterServlet extends AbstractDatabaseServlet {

    /**
     * Handles the search request for waiter reviews via POST.
     *
     * @param req the request from the user.
     * @param res the response to the user.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an input or output error occurs.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.SEARCH_WAITER_REVIEWS);

        HttpSession session = req.getSession(false);
        String waiterID = (session != null) ? (String) session.getAttribute("user") : null;

        List<Review> rl = null;
        Message m = null;

        try {
            rl = new SearchReviewDAO(getConnection(), waiterID, null, null)
                    .access().getOutputParam();

            m = new Message("Reviews successfully searched");
            LOGGER.info("Reviews successfully searched for waiter: %s", waiterID);

        } catch (SQLException ex) {
            m = new Message("Cannot search for reviews: unexpected error while accessing the database",
                    "E200", ex.getMessage());
            LOGGER.error("Cannot search for reviews.", ex);
        }

        try {
            req.setAttribute("reviewList", rl);
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/protected/search-waiter-review-result.jsp").forward(req, res);
        } catch (Exception ex) {
            LOGGER.error("Unable to send response when searching waiter reviews.", ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
        }
    }
}