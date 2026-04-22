package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchReviewByWaiterDAO;
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

@WebServlet(name = "SearchReviewByWaiterServlet", value = "/review/search-by-waiter")
public final class SearchReviewByWaiterServlet extends AbstractDatabaseServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction("SEARCH_WAITER_REVIEWS");

        HttpSession session = req.getSession(false);
        String waiterID = (String) session.getAttribute("user");

        List<Review> rl = null;
        Message m = null;

        try {
            rl = new SearchReviewByWaiterDAO(getConnection(), waiterID)
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
            req.getRequestDispatcher("/jsp/search-waiter-review-result.jsp").forward(req, res);
        } catch (Exception ex) {
            LOGGER.error("Unable to send response when searching waiter reviews.", ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
        }
    }
}