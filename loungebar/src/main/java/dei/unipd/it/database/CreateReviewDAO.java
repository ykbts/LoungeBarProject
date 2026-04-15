package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CreateReviewDAO extends AbstractDAO<Review> {

    private static final String STATEMENT = "INSERT INTO lounge.Review (waiterID, customerID, score, comment,) VALUES (?, ?, ?, ?, ?) RETURNING *";
    private final Review Review;

    public CreateReviewDAO (final Connection connection, final Review Review) {
        super(connection);

        if (review == null) {
            LOGGER.error("The review cannot be null.");
            throw new NullPointerException("The review cannot be null.");
        }
        this.review = review;
    }

    /** prepare and execute the query */
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
                r = new Review (rs.getString("waiterID"),
                        rs.getString("customerID"),
                        rs.getInt("score"),
                        rs.getString("comment"),

                LOGGER.info("Review for waiter {} successfully stored.", r.getWaiterID());
            }
        }

        outputParam = r;
    }
}