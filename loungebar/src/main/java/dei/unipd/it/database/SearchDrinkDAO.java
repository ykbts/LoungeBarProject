package dei.unipd.it.database;

import dei.unipd.it.resources.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchDrinkDAO extends AbstractDAO<List<Drink>> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lounge.Drink";

    private final String name;
    private final double price;
    private final Boolean is_Alcoholic;


    public SearchDrinkDAO(final Connection con, String name, double price, Boolean is_Alcoholic) {
        super(con);
        this.name = name;
        this.price = price;
        this.is_Alcoholic = is_Alcoholic;
    }

    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        // the results of the search
        final List<Drink> drinkList = new ArrayList<>();
        final List<String> clauses= new ArrayList<>();


        if(name != null){ clauses.add(" lounge.Drink.name = ? "); }
        if(price > 0){ clauses.add(" lounge.Drink.price = ? "); }
        if(is_Alcoholic != null ){ clauses.add(" lounge.Drink.is_alcoholic = ? "); }

        StringBuilder sb = new StringBuilder();
        sb.append(STATEMENT);
        sb.append(" ");

        if(!clauses.isEmpty()){
            sb.append(" WHERE ");
            boolean first= true;
            for(String s : clauses){
                if(first){
                    first=false;
                    sb.append(s);
                }else {
                    sb.append(" AND ");
                    sb.append(s);
                }
            }
        }
        sb.append(";");




        try {
            pstmt = conn.prepareStatement(sb.toString());

            int clauseCount = 1;

            if(name != null) {
                pstmt.setString(clauseCount, name);
                clauseCount += 1;
            }
            if(price > 0) {
                pstmt.setObject(clauseCount, price);
                clauseCount+=1;

            }
            if(is_Alcoholic != null){
                pstmt.setBoolean(clauseCount, is_Alcoholic);
                clauseCount+=1;
            }


            rs = pstmt.executeQuery();

            while (rs.next()) {

                drinkList.add(new Drink(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_Alcoholic")));
            }

            LOGGER.info("Drink(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = drinkList;
    }
}
