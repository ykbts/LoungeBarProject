package dei.unipd.it.servlets;

import dei.unipd.it.database.CreateShiftDAO;
import dei.unipd.it.resources.Shift;
import dei.unipd.it.resources.Message;
import dei.unipd.it.resources.LogContext;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;

@WebServlet(name = "CreateShiftServlet", value = "/shift/*")
public final class CreateShiftServlet extends AbstractDatabaseServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        String pathInfo = req.getPathInfo();
        String op = (pathInfo != null) ? pathInfo.replace("/", "") : "";

        LogContext.setAction("SHIFT_" + op.toUpperCase());

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

                // Виклик DAO
                s = new CreateShiftDAO(getConnection(), s).access().getOutputParam();

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