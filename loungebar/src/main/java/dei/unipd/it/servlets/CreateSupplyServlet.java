package dei.unipd.it.servlets;

import dei.unipd.it.database.CreateSupplyDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Supply;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.LogContext;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;

/**
 * Servlet for creating a new supply.
 * * <p>It reads the supply name and quantity, saves them to the database
 * using a DAO and forwards the result to a JSP page.</p>
 */
@WebServlet(name = "CreateSupplyServlet", value = "/supply/*")
public final class CreateSupplyServlet extends AbstractDatabaseServlet {

    /**
     * Handles the creation of a supply via POST.
     * * @param req the request from the user.
     * @param res the response to the user.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an input or output error occurs.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        String pathInfo = req.getPathInfo();
        String op = (pathInfo != null) ? pathInfo.replace("/", "") : "";

        LogContext.setAction(Actions.CREATE_SUPPLY);

        String name = null;
        int unitsAvailable = -1;

        Supply s = null;
        Message m = null;

        try {
            if ("create".equals(op)) {
                name = req.getParameter("name");

                String unitsParam = req.getParameter("units");
                if (unitsParam != null) {
                    unitsAvailable = Integer.parseInt(unitsParam);
                }

                LogContext.setResource(name);

                s = new Supply(name, unitsAvailable);

                new CreateSupplyDAO(getConnection(), s).access();

                m = new Message(String.format("Supply %s successfully created.", name));
                LOGGER.info("Supply %s successfully created in the database.", name);

            } else {
                m = new Message("Operation not supported.", "E404", String.format("The operation %s is invalid.", op));
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException ex) {
            m = new Message(
                    "Cannot create the supply. Invalid input parameters: units must be an integer.",
                    "E100", ex.getMessage());

            LOGGER.error("Invalid input parameters for supply creation.", ex);
        } catch (SQLException ex) {
            String sqlState = ex.getSQLState();

            if ("23505".equals(sqlState)) {
                m = new Message(String.format("Cannot create the supply: supply %s already exists.", name),
                        "E300", ex.getMessage());
                LOGGER.error(new StringFormattedMessage("Supply %s already exists.", name), ex);

            } else if ("23514".equals(sqlState)) {
                m = new Message(String.format("Cannot create the supply: invalid value for units. It must be 0 or more."),
                        "E101", ex.getMessage());
                LOGGER.error(new StringFormattedMessage("Check constraint violation for supply %s: %s", name, ex.getMessage()));

            } else {
                m = new Message("Unexpected database error while creating the supply.",
                        "E200", ex.getMessage());
                LOGGER.error("Database error during supply creation.", ex);
            }
        }

        try {
            req.setAttribute("supply", s);
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/create-supply-result.jsp").forward(req, res);

        } catch(Exception ex) {
            LOGGER.error(new StringFormattedMessage("Unable to send response for supply %s.", name), ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }
}