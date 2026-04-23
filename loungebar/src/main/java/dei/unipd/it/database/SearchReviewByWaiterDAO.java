package dei.unipd.it.database;

import dei.unipd.it.resources.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class SearchReviewByWaiterDAO extends AbstractDAO<List<Review>> {

    /**
     * SQL statement to select reviews for a specific waiter
     */
    private static final String STATEMENT =
            "SELECT user_waiterID, user_customerID, score, comment " +
                    "FROM lounge.Review " +
                    "WHERE user_waiterID = ?";

    private final String waiterID;

    public SearchReviewByWaiterDAO(final Connection con, final String waiterID) {
        super(con);
        this.waiterID = waiterID;
    }

    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Review> reviews = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, waiterID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getString("user_waiterID"),
                        rs.getString("user_customerID"),
                        rs.getShort("score"),
                        rs.getString("comment")
                ));
            }

            LOGGER.info("Reviews successfully retrieved for waiter: %s", waiterID);

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = reviews;
    }
}