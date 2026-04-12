package dei.unipd.it.database;

import java.sql.SQLException;

public interface DataAccessObject<T> {

    /** accesses to the database */
    DataAccessObject<T> access() throws SQLException;

    /** retrieve any output parameter after accessing the database */
    T getOutputParam();

}
