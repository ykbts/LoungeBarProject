package dei.unipd.it.servlets;

import dei.unipd.it.database.CreateShiftDAO;
import dei.unipd.it.database.CreateWorkDAO;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.Shift;
import dei.unipd.it.resources.Work;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.LogContext;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.StringFormattedMessage;

/**
 * Servlet for creating a new work shift.
 * * <p>It reads shift times from the request, saves the shift via DAO,
 * and forwards the result to a JSP page.</p>
 */
@WebServlet(name = "CreateShiftServlet", value = "/shift/*")
public final class CreateShiftServlet extends AbstractDatabaseServlet {

    /**
     * Handles the creation of a shift via POST.
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

        LogContext.setAction(Actions.CREATE_SHIFT);

        Shift s = null;
        Message m = null;

        try {
            if ("create".equals(op)) {
                String startTimeParam = req.getParameter("start_time");
                String endTimeParam = req.getParameter("end_time");

                if (startTimeParam == null || endTimeParam == null) {
                    throw new IllegalArgumentException("Start time and end time are required.");
                }

                LocalDateTime start = LocalDateTime.parse(startTimeParam);
                LocalDateTime end = LocalDateTime.parse(endTimeParam);

                s = new Shift(-1, start, end);

                s = new CreateShiftDAO(getConnection(), s).access().getOutputParam();

                
                HttpSession session = req.getSession(false);
                String waiterID = (session != null) ? (String) session.getAttribute("user") : null;
                if (waiterID == null) {
                    throw new IllegalArgumentException("No waiter found in session.");
                }
                new CreateWorkDAO(getConnection(), new Work(waiterID, s.getId())).access();

                m = new Message(String.format("Shift %d successfully created.", s.getId()));
                LogContext.setResource(String.valueOf(s.getId()));
                LOGGER.info("Shift %d successfully created in the database.", s.getId());

            } else {
                m = new Message("Operation not supported.", "E404", String.format("The operation %s is invalid.", op));
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (IllegalArgumentException ex) {
            m = new Message("Cannot create the shift. Invalid input parameters: " + ex.getMessage(),
                    "E100", ex.getMessage());
            LOGGER.error("Invalid input parameters for shift creation.", ex);
        } catch (SQLException ex) {
            if ("23514".equals(ex.getSQLState())) {
                m = new Message("Cannot create the shift: duration must be between 1 and 8 hours.",
                        "E301", ex.getMessage());
            } else {
                m = new Message("Unexpected database error while creating the shift.",
                        "E200", ex.getMessage());
            }
            LOGGER.error("Database error during shift creation.", ex);
        }

        try {
            req.setAttribute("shift", s);
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/protected/create-shift-result.jsp").forward(req, res);

        } catch(Exception ex) {
            LOGGER.error("Unable to send response for shift creation.", ex);
            throw ex;
        } finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }
}