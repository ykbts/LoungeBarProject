package dei.unipd.it.database;

import dei.unipd.it.resources.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for creating a review.
 *
 * <p>Saves waiter ID, customer ID, score and comment.</p>
 *
 * <p>Returns the created {@link Review}.</p>
 */
public class CreateReviewDAO extends AbstractDAO<Review> {

    /**
     * SQL statement used to insert a new review and return the created record.
     */
    private static final String STATEMENT = "INSERT INTO lounge.Review (user_waiterID, user_customerID, score, comment) VALUES (?, ?, ?, ?) RETURNING *";

    /**
     * The review data to be persisted.
     */
    private final Review review;

    /**
     * Creates a new DAO for inserting a specific review.
     *
     * @param connection the database connection
     * @param review the review object containing the data to be stored
     * @throws NullPointerException if {@code review} is null
     */
    public CreateReviewDAO (final Connection connection, final Review review) {
        super(connection);

        if (review == null) {
            LOGGER.error("The review cannot be null.");
            throw new NullPointerException("The review cannot be null.");
        }
        this.review = review;
    }

    /**
     * Creates a review in the database.
     *
     * <p>Sets review data, executes the insert, and returns the saved {@link Review}.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    protected final void doAccess() throws SQLException {

        ResultSet rs;
        Review r = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(STATEMENT)) {

            preparedStatement.setString(1, review.getWaiterID());
            preparedStatement.setString(2, review.getCustomerID());
            preparedStatement.setInt(3, review.getScore());
            preparedStatement.setString(4, review.getComment());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                r = new Review (rs.getString("user_waiterID"),
                        rs.getString("user_customerID"),
                        rs.getInt("score"),
                        rs.getString("comment")
                );

                LOGGER.info("Review for waiter {} successfully stored.", r.getWaiterID());
            }
        }

        outputParam = r;
    }
}