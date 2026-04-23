package dei.unipd.it.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

/**
 * Servlet for the home page.
 *
 * <p>It checks if a user is logged in by looking at their role.</p>
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends AbstractDatabaseServlet {

    /**
     * Handles the request to view the home page.
     *
     * @param req the request from the user.
     * @param res the response to the user.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an input or output error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        
        String role = (String) req.getSession().getAttribute("role");

        
        if (role == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        
        req.getRequestDispatcher("/jsp/protected/home.jsp").forward(req, res);
    }
}

