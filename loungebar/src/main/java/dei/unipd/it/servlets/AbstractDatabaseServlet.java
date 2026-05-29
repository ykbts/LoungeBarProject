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

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 */
public class AbstractDatabaseServlet extends HttpServlet {

	/**
	 * A LOGGER available for all the subclasses.
	 */
	protected /*static*/ final Logger LOGGER = LogManager.getLogger(/*AbstractDatabaseServlet.class*/getClass()/*, StringFormatterMessageFactory.INSTANCE*/);

	/**
	 * The connection pool to the database.
	 */
	private DataSource ds;
	protected static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

	/**
	 * Gets the {@code DataSource} for managing the connection pool to the database.
	 *
	 * @param config a {@code ServletConfig} object containing the servlet's configuration and initialization
	 *               parameters.
	 *
	 * @throws ServletException if an exception has occurred that interferes with the servlet's normal operation
	 */
	public void init(ServletConfig config) throws ServletException {

		// the JNDI lookup context
		InitialContext cxt;

		try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/bar");

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


