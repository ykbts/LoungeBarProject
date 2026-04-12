package dei.unipd.it.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO<T> implements DataAccessObject<T> {

    protected static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class, StringFormatterMessageFactory.INSTANCE);
    protected final Connection conn;
    protected T outputParam = null;
    private boolean isAccessed = false;
    private final Object lock = new Object();

    /** create a new DAO */
    protected AbstractDAO(final Connection conn) {

        if (conn == null) {
            String message = "The connection cannot be null.";
            LOGGER.error(message);
            throw new NullPointerException(message);
        }

        this.conn = conn;

        try {
            // ensure that autocommit is true
            conn.setAutoCommit(true);
            LOGGER.debug("Autocommit set to value TRUE");
        } catch (SQLException e) {
            LOGGER.warn("Unable to set connection auto-commit to true.", e);
        }
    }

    public final DataAccessObject<T> access() throws SQLException {

        synchronized (lock) {
            try {
                // try access; no more than one
                if (isAccessed) {
                    String message = "Cannot use a DataAccessObject more than once.";
                    LOGGER.error(message);
                    throw new SQLException(message);
                }
            } finally {
                isAccessed = true;
            }
        }
        try {
            /* real concrete access:
             verrà implementato DOPO nella classe derivata, però nel mio servlet io chiamerò questo metodo
             */
            doAccess();

            try {
                // close the connection
                conn.close();
                LOGGER.debug("Connection closed successfully.");

            } catch (SQLException e) {
                LOGGER.error("Unable to close connection.", e);
                throw e;
            }
        } catch (final Throwable t) {

            LOGGER.error("Unable to perform the requested database access operation.", t);

            try {
                // autoCommit == false => transaction needs to be rolled back
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                    LOGGER.info("Transaction successfully rolled-back.");
                }
            }
            catch (SQLException e) {
                LOGGER.error("Unable to rollback the transaction.", e);
            }
            finally {
                try {
                    conn.close();
                    LOGGER.debug("Connection closed successfully.");
                }
                catch (SQLException e) {
                    LOGGER.error("Unable to close connection.", e);
                }
            }

            if (t instanceof SQLException)
                throw (SQLException) t;
            else
                throw new SQLException("Unable to perform the requested database access operation.", t);

        }

        return this;
    }

    @Override
    public final T getOutputParam() {

        synchronized (lock) {
            if (!isAccessed) {
                String message = "Cannot retrieve the output parameter before accessing the database.";
                LOGGER.error(message);
                throw new IllegalStateException(message);
            }
        }
        return outputParam;
    }


    /** perform logical access; it's ABSTRACT, subclass must implement it */
    protected abstract void doAccess() throws SQLException;
}
