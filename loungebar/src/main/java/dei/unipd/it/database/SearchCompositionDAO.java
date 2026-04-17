package dei.unipd.it.database;

import dei.unipd.it.resources.Composition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchCompositionDAO extends AbstractDAO<List<Composition>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT drink_name, supply_name, amount FROM lounge.Composition";

    private final String drinkName;
    private final String supplyName;
    private final Integer amount;

    public SearchCompositionDAO(final Connection con, String drinkName, String supplyName, Integer amount) {
        super(con);
        this.drinkName = drinkName;
        this.supplyName = supplyName;
        this.amount = amount;
    }

    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Composition> compositionList = new ArrayList<>();
        final List<String> clauses = new ArrayList<>();

        // Build dynamic clauses based on provided parameters
        if (drinkName != null) { clauses.add(" lounge.Composition.drink_name = ? "); }
        if (supplyName != null) { clauses.add(" lounge.Composition.supply_name = ? "); }
        if (amount != null && amount > 0) { clauses.add(" lounge.Composition.amount = ? "); }

        StringBuilder sb = new StringBuilder(STATEMENT);

        if (!clauses.isEmpty()) {
            sb.append(" WHERE ");
            for (int i = 0; i < clauses.size(); i++) {
                sb.append(clauses.get(i));
                if (i < clauses.size() - 1) {
                    sb.append(" AND ");
                }
            }
        }
        sb.append(";");

        try {
            pstmt = conn.prepareStatement(sb.toString());

            int parameterIndex = 1;

            if (drinkName != null) {
                pstmt.setString(parameterIndex++, drinkName);
            }
            if (supplyName != null) {
                pstmt.setString(parameterIndex++, supplyName);
            }
            if (amount != null && amount > 0) {
                pstmt.setInt(parameterIndex++, amount);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                compositionList.add(new Composition(
                        rs.getString("drink_name"),
                        rs.getString("supply_name"),
                        rs.getInt("amount")));
            }

            LOGGER.info("Composition(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = compositionList;
    }
}