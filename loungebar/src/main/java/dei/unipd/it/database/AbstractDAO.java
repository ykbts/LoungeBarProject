package dei.unipd.it.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * Provides support for the actual implementation of a DAO object.
 *
 * <p>
 * {@code AbstractDAO} is designed to achieve the following goals:
 * <ol>
 * <li>a consistent way of accessing the database and managing exceptions is
 * guaranteed for all of the classes that need to directly interact with the
 * DBMS.
 * <li>independence from any particular DBMS is achieved, meaning that the
 * DBMS-dependent code is as isolated as possible and that upper layers can
 * transparently change the underlying DBMS, without the need of modifying their
 * code.
 * </ol>
 * </p>
 *
 * <p>
 * With respect to the first goal, {@code AbstractDAO}
 * provides a basic infrastructure which manages the connection, the commitment
 * of transactions (or the roll-back), if needed, and any exception that may be
 * thrown while accessing the database.
 * </p>
 *
 * <p>
 * With respect to the second goal,{@code AbstractDAO} is the
 * root of a hierarchy of helper classes, which isolate as much as possible the
 * DBMS-dependent code.
 * </p>
 *
 *
 * <p>
 * Concrete subclasses have to implement the abstract method
 * {@link #doAccess()} in order to define the needed
 * logic for accessing the database. This method is, in turn, called by
 * {@link AbstractDAO#access()}, which provides a consistent way of
 * accessing the database and managing exceptions.
 * </p>
 *
 * <p>
 * Concrete sub-classes may also override {@link AbstractDAO#access()}
 * in order to provide a
 * different management of connection and transaction if they have peculiar
 * needs. Nevertheless, the objective of this class is to ease sub-classes by
 * providing a pre-packed logic for connection and transaction management, that
 * is what {@link AbstractDAO#access()} does.
 * </p>
 *
 * @param <T> the type of the output parameter returned by the DAO.
 *
 */
public abstract class AbstractDAO<T> implements DataAccessObject<T> {

    /**
     * A LOGGER available for all the subclasses.
     */
    protected /*static*/ final Logger LOGGER = LogManager.getLogger(/*AbstractDAO.class*/getClass()/*, StringFormatterMessageFactory.INSTANCE*/);

    /**
     * The connection to be used to access the database.
     */
    protected final Connection conn;

    /**
     * The output parameter, if any
     */
    protected T outputParam = null;

    /**
     * Indicates whether the database has been already accessed or not.
     */
    private boolean isAccessed = false;

    /**
     * A lock for synchronization.
     */
    private final Object lock = new Object();

    /**
     * Creates a new DAO object.
     *
     * @param conn the connection to be used for accessing the database.
     */
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
