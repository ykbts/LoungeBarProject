package dei.unipd.it.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dei.unipd.it.resources.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.message.StringFormattedMessage;

import dei.unipd.it.database.CreateUserDAO;
import dei.unipd.it.database.SearchUserDAO;
import dei.unipd.it.database.SearchUserLogInDAO;
import dei.unipd.it.filter.ProtectedResourceFilter;
import dei.unipd.it.resources.LogContext;
import dei.unipd.it.resources.User;
import dei.unipd.it.resources.UserRole;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/signup"})
public class LoginServlet extends AbstractDatabaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String role = req.getParameter("role"); 
        req.setAttribute("role", role); 

        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setResource(req.getRequestURI());

        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("/") + 1);
        LOGGER.info(new StringFormattedMessage("operation performed %s.", op));


        switch (op){

            case "login":
                LogContext.setAction("LOGIN");
                loginOperations(req, res, false);
                break;
            case "signup":
                LogContext.setAction("SIGNUP");
                registrationOperations(req, res);
                break;
            default:
                Message m = new Message("An error occurred default","E200","Operation Unknown");
                LOGGER.error(new StringFormattedMessage("stacktrace:  %s.", m.getMessage()));
        }




    }



    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        // 1. Logs and config
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setResource(req.getRequestURI());

        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("/") + 1);
        LOGGER.info(new StringFormattedMessage("operation: %s.", op));

        // Get role
        String role = req.getParameter("role");
        req.setAttribute("role", role); 

        switch (op) {
            case "login":
                LogContext.setAction("LOGIN");
                
                loginOperations(req, res, false); 
                break;

            case "signup":
                LogContext.setAction("SIGNUP");
                registrationOperations(req, res);
                break;

            default:
                Message m = new Message("An error occurred default", "E200", "Operation Unknown");
                LOGGER.error(new StringFormattedMessage("stacktrace: %s.", m.getMessage()));
        }
    }

public void loginOperations(HttpServletRequest req, HttpServletResponse res, boolean isValid) throws ServletException, IOException {
        String op="login";
        List<User> userList=null;
        Message m = null;

        String regex_psw = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";

        String role = req.getParameter("role");
        req.setAttribute("role", role); // Save it again

        try {
            //take from the request, the parameters
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            LOGGER.info(new StringFormattedMessage("user %s is trying to login", username));


            if(isValid){
                op="register";
                // try to find the user in the database
                password=org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); //hashing the password
                username=username.toLowerCase();

                // try to find the user in the database using also the role
                userList = new SearchUserDAO(getConnection(), username, password,null, null,null, role).access().getOutputParam();

                //the UserDAO will tell us if the username exists and the password
                //matches
                if (userList.size() < 1){
                    //if not, tell it to the user
                    m = new Message("The user does not exist","E200","Missing user");
                    LOGGER.error(new StringFormattedMessage("problems with user:  %s.", m.getMessage()));

                }
                else{
                    HttpSession session = req.getSession();
                    session.setAttribute(ProtectedResourceFilter.USER_ATTRIBUTE, username);

                    session.setAttribute("role", role);

                    m = new Message("Login success");
                    LOGGER.info(new StringFormattedMessage("the USER %s LOGGED IN", userList.get(0).getUsername()));

                }
            }else{
                if (username == null || username.equals("")) {
                    //the username is null (was not set on the parameters) or an empty string
                    //notify this to the user
                    m = new Message("Insert a username","E200","Missing fields");
                    LOGGER.error(new StringFormattedMessage("problems with fields:  %s.", m.getMessage()));


                } else if (password == null || password.equals("")) {
                    //the password was empty

                    m = new Message("Insert the password","E200","Missing fields");
                    LOGGER.error(new StringFormattedMessage("problems with fields:  %s.", m.getMessage()));

                }
                // check password is compliant
                else if (!password.matches(regex_psw)){
                    m = new Message("This password is not compliant","E200","Password not compliant");

                    LOGGER.error(new StringFormattedMessage("problems with fields:  %s.", m.getMessage()));

                }
                // check username is compliant
                else if (username.length()<3){
                    m = new Message("This is not a valid username","E200","Username not compliant");
                    LOGGER.error(new StringFormattedMessage("problems with fields:  %s.", m.getMessage()));

                }
                else{

                    //try to authenticate the user
                    // try to find the user in the database
                    password=org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); //hashing the password
                    username=username.toLowerCase();
                    User user = new SearchUserLogInDAO(getConnection(), username, password).access().getOutputParam();



                    //the UserDAO will tell us if the username exists and the password
                    //matches
                    if (user == null) {
                    m = new Message("Invalid username or password.", "E200", "Authentication failed");
                    LOGGER.warn("Login failed: Credentials do not match for user {}", username);
                }
                    else {
                
                HttpSession session = req.getSession();
                
                
                session.setAttribute(ProtectedResourceFilter.USER_ATTRIBUTE, user.getUsername());
                session.setAttribute("role", user.getRole().toString());
                
                m = new Message("Login successful!");
                LOGGER.info("User {} logged in successfully as {}", username, user.getRole());

                    }
                }
            }

        } catch (SQLException e){
            m = new Message("An error occurred SQL","E200",e.getMessage());
            LOGGER.error("stacktrace:", e);
        }
        catch (NumberFormatException e){
            m = new Message("An error occurred handling numbers","E200",e.getMessage());
            LOGGER.error("stacktrace:", e);
        }
        finally{
           if(m.isError()){
               // in case of error we go back to the login page
               req.setAttribute("role", role); 
            req.setAttribute("message", m);

            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }else{
               // else we go to the home page
               req.getRequestDispatcher("/").forward(req, res);
           }

            LogContext.removeIPAddress();
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }




public void registrationOperations(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    User u = null;
    Message m = null;
    
    
    
     

    try {
        
        //get the registration parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password_check = req.getParameter("password_check");
        String name = req.getParameter("firstname"); 
        String surname = req.getParameter("surname");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        UserRole roleEnum = UserRole.fromString(role);
        req.setAttribute("role", role);

         
        LOGGER.info(new StringFormattedMessage("New registration attempt: %s as %s", username, role));

        String regex_psw = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";

    if (username == null || username.equals("") || 
        password == null || password.equals("") || 
        role == null || role.equals("")) {

        m = new Message("Some fields are empty", "E200", "Missing fields");
        LOGGER.error(new StringFormattedMessage("Problems with fields: %s.", m.getMessage()));
    }
    
    else if (!password.matches(regex_psw)) {
        m = new Message("This password is not compliant", "E200", "Password not compliant");
        LOGGER.error("Password validation failed for " + username);
    }
    
    else if (!password.equals(password_check)) {
        m = new Message("Passwords do not match", "E200", "Password mismatch");
        LOGGER.error("Password mismatch for " + username);
    }
    
    else if (username.length() < 3) {
        m = new Message("Username is too short", "E200", "Username not compliant");
        LOGGER.error("Username too short: " + username);
    }
    // ALL CORRECT
    else {
        try {
        username = username.toLowerCase();
        password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); // Hashing

        // Check if user exists with the same username and role
        List<User> usersList = new SearchUserDAO(getConnection(), username, null, null, null, null, role)
                                    .access().getOutputParam();

        if (usersList.size() != 0) {
            m = new Message("This user already exists as " + role, "E200", "User already existing");
            LOGGER.error("Registration failed: User already exists.");
        } 
        else {
            // We create it if it doesn't exist
            User newUser = new User(username, password,name, surname, phone, roleEnum); 
            LOGGER.info("Attempting to insert user: {} with role: {}", username, roleEnum);
            
            new CreateUserDAO(getConnection(), newUser).access();
            LOGGER.info(new StringFormattedMessage("REGISTERED NEW %s: %s.", role.toUpperCase(), username));

            // Autologin
            loginOperations(req, res, true);
            return; 
        }

    
        
   } catch (Exception e) {
        m = new Message("Error during registration", "E500", e.getMessage());
        LOGGER.error("Registration error", e);
    } 
}
} catch (Exception e) { // Este catch cierra el TRY principal
        m = new Message("General error", "E500", e.getMessage());
        LOGGER.error("Unexpected error", e);
 } finally {
        
        if (m != null) {
            req.setAttribute("message", m);
            req.setAttribute("role", req.getParameter("role"));
            req.getRequestDispatcher("/jsp/signup.jsp").forward(req, res);
        }
        LogContext.removeIPAddress();
        LogContext.removeAction();
        LogContext.removeResource();
    }
}
}




