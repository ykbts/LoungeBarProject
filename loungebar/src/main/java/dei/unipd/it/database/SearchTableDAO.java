package dei.unipd.it.database;
import dei.unipd.it.resources.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchTableDAO extends AbstractDAO<List<Tables>> {
	
	private static final String STATEMENT = "SELECT * FROM lounge.Tables";
	private final int number;
    private final int capacity;
    private final boolean isAvaliable;


	public SearchTableDAO(final Connection con, int number, int capacity, boolean isAvaliable) {
		super(con);
		this.number = number;
        this.capacity = capacity;
        this.isAvaliable = isAvaliable;
	}

	@Override
	public final void doAccess() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		final List<Tables> tables = new ArrayList<>();
		final List<String> clauses= new ArrayList<>();


		if(number>=0){ clauses.add(" lounge.Tables.number = ? "); }
		if(capacity>=0){ clauses.add(" lounge.Tables.capacity = ? "); }
		if(isAvaliable){ clauses.add(" lounge.Tables.is_available = ? "); }

		StringBuilder sb = new StringBuilder();
		sb.append(STATEMENT);
		sb.append(" ");

		if(!clauses.isEmpty()){

			sb.append(" WHERE ");
			boolean first= true;

			for(String s : clauses){

				if (first){
					first=false;
					sb.append(s);
				}
				else {
					sb.append(" AND ");
					sb.append(s);
				}
			}
		}
		sb.append(";");




		try {
			pstmt = conn.prepareStatement(sb.toString());

			int clauseCount = 1;

			if(number>=0) {
				pstmt.setInt(clauseCount, number);
				clauseCount += 1;
			}
			if(capacity>=0){
				pstmt.setInt(clauseCount, capacity);
				clauseCount += 1;
			}
			if(isAvaliable){
				pstmt.setBoolean(clauseCount, isAvaliable);
				clauseCount += 1;
			}
			
			




			rs = pstmt.executeQuery();

			while (rs.next()) {
				tables.add(new Tables(rs.getInt("number"), rs.getInt("capacity"), rs.getBoolean("is_available")));
			}
	
			LOGGER.info("Table(s) successfully listed.");
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

		}

		this.outputParam = tables;
	}
}

