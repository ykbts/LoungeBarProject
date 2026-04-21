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
        
        // 1. Recuperamos el rol de la sesión (lo guardamos en el Login)
        String role = (String) req.getSession().getAttribute("role");

        // 2. Si no hay rol, es que no está logueado -> Al login
        if (role == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 3. Si todo está bien, mostramos la vista
        req.getRequestDispatcher("/jsp/home.jsp").forward(req, res);
    }
}

