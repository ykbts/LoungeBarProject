package dei.unipd.it.database;

import dei.unipd.it.resources.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateMessageDAO extends AbstractDAO<Message> {


    private static final String STATEMENT = "INSERT INTO lounge.Message (message, errorCode, errorDetails, isError) VALUES (?, ?, ?) RETURNING *";
    private final Message message;

    public CreateMessageDAO (final Connection connection, final Message message) {
        super(connection);

        if (message == null) {
            LOGGER.error("The message cannot be null.");
            throw new NullPointerException("The message cannot be null.");
        }
        this.message = message;
    }

    /** prepare and execute the query */
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Message r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setString(2, message.getErrorCode());
            preparedStatement.setString(3, message.getErrorDetails());
            preparedStatement.setBoolean(4, message.isError());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Message (
                        rs.getString("message"),
                        rs.getString("errorCode"),
                        rs.getString("errorDetails"),
                        rs.getBoolean("isError"));

                LOGGER.info("Message {} successfully stored.", r.getName());
            }
        }

        outputParam = r;
    }
}