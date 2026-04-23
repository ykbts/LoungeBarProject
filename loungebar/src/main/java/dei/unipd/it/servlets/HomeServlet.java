package dei.unipd.it.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends AbstractDatabaseServlet {

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

