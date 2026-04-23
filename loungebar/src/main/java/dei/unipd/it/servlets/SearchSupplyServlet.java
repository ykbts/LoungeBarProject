package dei.unipd.it.servlets;

import dei.unipd.it.database.SearchSupplyDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Supply;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;

/**
 * Servlet for searching bar supplies
 */
@WebServlet(name = "SearchSupplyServlet", value = "/supply/search")
public final class SearchSupplyServlet extends AbstractDatabaseServlet {

    /**
     * Handles the supply search via POST.
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
        LogContext.setAction(Actions.SEARCH_SUPPLY);

        String name = null;
        int unitsAvailable = -1;

        List<Supply> sl = null;
        Message m = null;

        try {
            name = req.getParameter("name");
            if (name != null && name.trim().isEmpty()) {
                name = null;
            }

            String unitsParam = req.getParameter("units");

            if (unitsParam != null && !unitsParam.trim().isEmpty()) {
                try {
                    unitsAvailable = Integer.parseInt(unitsParam);
                } catch (NumberFormatException e) {
                    unitsAvailable = -1;
                }
            }

            sl = new SearchSupplyDAO(getConnection(), name, unitsAvailable).access().getOutputParam();

            m = new Message("Supplies successfully searched");

            LOGGER.info("Supplies successfully searched. Name: %s, Units: %d", name, unitsAvailable);

        } catch (NumberFormatException ex) {
            m = new Message("Cannot search for supplies. Invalid input parameters: units must be an integer.", "E100",
                    ex.getMessage());

            LOGGER.error("Cannot search for supplies. Invalid input parameters: units must be an integer.", ex);
        } catch (SQLException ex) {
            m = new Message("Cannot search for supplies: unexpected error while accessing the database", "E200",
                    ex.getMessage());

            LOGGER.error("Cannot search for supplies: unexpected error while accessing the database.", ex);
        }

        try {
            req.setAttribute("supplyList", sl);
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/search-supply-result.jsp").forward(req, res);

        } catch(Exception ex) {
            LOGGER.error(new StringFormattedMessage("Unable to send response when searching supplies."), ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
        }
    }
}