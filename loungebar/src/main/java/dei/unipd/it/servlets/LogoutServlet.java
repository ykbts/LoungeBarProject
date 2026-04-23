package dei.unipd.it.servlets;

import dei.unipd.it.filter.ProtectedResourceFilter;
import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Logs out the current user and redirect him to the home page
 */
public class LogoutServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.LOGOUT);
        String user = null;

        // logging out
        try {
            HttpSession session = req.getSession(false);

            // if the session is null, do nothing
            if (session != null) {
                user = (String) session.getAttribute(ProtectedResourceFilter.USER_ATTRIBUTE);

                if (user != null) {
                    LogContext.setUser(user);
                    LOGGER.info("User {} logged out.", user);
                }
                else
                    LOGGER.info("Logout attempt from non-authenticated session.");

                // user info no more stored in the session -> logout
                session.invalidate();
            }
        }
        catch (IllegalStateException ex) {
            LOGGER.error("Invalid session: no user to log out.", ex);
        }


        // redirect
        try {
            resp.sendRedirect(req.getContextPath() + "/");
        }
        catch (IOException e) {
            LOGGER.error("Unable to perform redirection after logout attempt.");
            throw e;
        }
        finally {
            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeUser();
        }
    }
}
