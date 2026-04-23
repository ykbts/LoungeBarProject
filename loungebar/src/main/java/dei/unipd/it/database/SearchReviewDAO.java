package dei.unipd.it.database;

import dei.unipd.it.resources.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for searching reviews.
 *
 * <p>Filters reviews by waiter, customer, or score.</p>
 *
 * <p>Returns a list of matching {@link Review} objects.</p>
 */
public final class SearchReviewDAO extends AbstractDAO<List<Review>> {

    /**
     * The base SQL statement to be executed.
     */
    private static final String STATEMENT = "SELECT user_waiterID, user_customerID, score, comment FROM lounge.Review";

    /**
     * The waiter identifier filter.
     */
    private final String waiterID;

    /**
     * The customer identifier filter.
     */
    private final String customerID;

    /**
     * The numerical score filter.
     */
    private final Integer score;

    /**
     * Creates a new DAO for searching reviews with optional filters.
     *
     * @param con the database connection
     * @param waiterID the ID of the waiter to filter by; if {@code null}, this filter is ignored
     * @param customerID the ID of the customer to filter by; if {@code null}, this filter is ignored
     * @param score the review score to match; if {@code null} or out of range (1-5), this filter is ignored
     */
    public SearchReviewDAO(final Connection con, String waiterID, String customerID, Integer score) {
        super(con);
        this.waiterID = waiterID;
        this.customerID = customerID;
        this.score = score;
    }

    /**
     * Searches reviews in the database.
     *
     * <p>Executes the query, and returns matching {@link Review} objects.</p>
     *
     * @throws SQLException if a database error occurs
     */
    @Override
    public final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Review> reviews = new ArrayList<>();
        final List<String> clauses = new ArrayList<>();

        // Determine which filters to apply
        if (waiterID != null) { clauses.add(" lounge.Review.user_waiterID = ? "); }
        if (customerID != null) { clauses.add(" lounge.Review.user_customerID = ? "); }
        if (score != null && score >= 1 && score <= 5) { clauses.add(" lounge.Review.score = ? "); }

        StringBuilder sb = new StringBuilder(STATEMENT);

        if (!clauses.isEmpty()) {
            sb.append(" WHERE ");
            boolean first = true;
            for (String s : clauses) {
                if (first) {
                    first = false;
                    sb.append(s);
                } else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");

        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;

            if (waiterID != null) {
                pstmt.setString(clauseCount++, waiterID);
            }
            if (customerID != null) {
                pstmt.setString(clauseCount++, customerID);
            }
            if (score != null && score >= 1 && score <= 5) {
                pstmt.setInt(clauseCount++, score);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getString("user_waiterID"),
                        rs.getString("user_customerID"),
                        rs.getInt("score"),
                        rs.getString("comment")
                ));
            }

            LOGGER.info("Review(s) successfully listed.");
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