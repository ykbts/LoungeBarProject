package dei.unipd.it.database;

import dei.unipd.it.resources.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DeleteReviewDAO extends AbstractDAO<Review> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM lounge.Review WHERE user_waiterID = ? AND user_customerID = ? RETURNING *;";

    private final Review review;

    public DeleteReviewDAO(final Connection con, final Review review) {
        super(con);

        if (review == null) {
            LOGGER.error("The review cannot be null");
            throw new NullPointerException("The review cannot be null");
        }

        if (review.getWaiterID() == null || review.getWaiterID().isBlank() ||
        review.getCustomerID() == null || review.getCustomerID().isBlank()) {
            LOGGER.error("Both waiter ID and customer ID must be provided");
            throw new IllegalArgumentException("Invalid IDs for deletion");
    }

        this.review = review;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Review deletedReview = null;

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, review.getWaiterID());
            pstmt.setString(2, review.getCustomerID());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                deletedReview = new Review(
                        rs.getString("user_waiterID"),
                        rs.getString("user_customerID"),
                        rs.getInt("score"),
                        rs.getString("comment")
                );

                LOGGER.info("Review from customer {} to waiter {} successfully deleted.", 
                            deletedReview.getCustomerID(), deletedReview.getWaiterID());
                LOGGER.warn("No review found for waiter {} and customer {}", 
                            review.getWaiterID(), review.getCustomerID());
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = deletedReview;
    }
}
