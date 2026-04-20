package dei.unipd.it.servlets;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDatabaseServlet extends HttpServlet {

    protected static final Logger logger = LogManager.getLogger(AbstractDatabaseServlet.class, StringFormatterMessageFactory.INSTANCE);
    private DataSource ds;


    public void init(ServletConfig config) throws ServletException {

		// the JNDI lookup context
		InitialContext cxt;

		try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/sigir-db");

			LOGGER.info("Connection pool to the database pool successfully acquired.");
		} catch (NamingException e) {
			ds = null;

			LOGGER.error("Unable to acquire the connection pool to the database.", e);

			throw new ServletException("Unable to acquire the connection pool to the database", e);
		}
	}

	/**
	 * Releases the {@code DataSource} for managing the connection pool to the database.
	 */
	public void destroy() {
		ds = null;
		LOGGER.info("Connection pool to the database pool successfully released.");
	}

	/**
	 * Returns a {@link  Connection} for accessing the database.
	 *
	 * @return a {@link Connection} for accessing the database
	 *
	 * @throws java.sql.SQLException if anything goes wrong in obtaining the connection.
	 */
	protected final Connection getConnection() throws SQLException {
		try {
			return ds.getConnection();
		} catch (final SQLException e) {
			LOGGER.error("Unable to acquire the connection from the pool.", e);
			throw e;
		}
	}

}

}
