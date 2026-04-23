/*
 * Copyright 2020-2023 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dei.unipd.it.filter;


import dei.unipd.it.resources.Actions;
import dei.unipd.it.resources.LogContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Base64;


public class ProtectedResourceFilter implements Filter {

    /**
     * A LOGGER available for all the subclasses.
     */
    protected static final Logger LOGGER = LogManager.getLogger(ProtectedResourceFilter.class,
            StringFormatterMessageFactory.INSTANCE);


    /**
     * The name of the user attribute in the session
     */
    public static final String USER_ATTRIBUTE = "user";

    /**
     * The configuration for the filter
     */
    private FilterConfig config = null;

    /**
     * The connection pool to the database.
     */
    private DataSource ds;


    @Override
    public void init(final FilterConfig config) throws ServletException {

        if (config == null) {
            LOGGER.error("Filter configuration cannot be null.");
            throw new ServletException("Filter configuration cannot be null.");
        }
        this.config = config;

        /*
        Here we could pass configuration parameters to the filter, if needed.
         */

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {

        LogContext.setIPAddress(servletRequest.getRemoteAddr());

        try {
            if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
                LOGGER.error("Only HTTP requests/responses are allowed.");
                throw new ServletException("Only HTTP requests/responses are allowed.");
            }

            // Safe to downcast at this point.
            final HttpServletRequest req = (HttpServletRequest) servletRequest;
            final HttpServletResponse res = (HttpServletResponse) servletResponse;

            LOGGER.info("request URL =  %s", req.getRequestURL());

            // forbid caching of restricted areas
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);

            final HttpSession session = req.getSession(false);

            // if we do not have a session, try to authenticate the user
            if (session == null) {

                LOGGER.warn("Authentication required to access resource {} with method {}.", req.getRequestURI(), req.getMethod());

                // try to authenticate the user
                //forwarding the request to the login page
                LOGGER.info("Forwarding the request to the login page");
                req.getRequestDispatcher("/jsp/login.jsp").forward(req,res);
                return;
            } else {

                final String user = (String) session.getAttribute(USER_ATTRIBUTE);

                // if the user is not in the session
                if (user == null || user.isBlank()) {

                    // invalidate the session
                    session.invalidate();

                    LOGGER.warn("Authentication required to access resource %s with method %s.  Session %s exists but no user found. Session invalidated.", req.getRequestURI(), req.getMethod(), session.getId());


                    // try to authenticate the user
                    //forwarding the request to the login page
                    LOGGER.info("Forwarding the request to the login page");
                    req.getRequestDispatcher("/jsp/login.jsp").forward(req,res);
                    return;
                }
            }

            // the user is properly authenticated and in session, continue the processing
            chain.doFilter(servletRequest, servletResponse);

        }
        catch (Exception e) {
            LOGGER.error("Unable to perform the protected resource filtering.", e);
            throw e;
        }
        finally {
            LogContext.removeUser();
            LogContext.removeIPAddress();
            LogContext.removeAction();
        }
    }

    @Override
    public void destroy() {
        config = null;
        ds = null;
    }



}

