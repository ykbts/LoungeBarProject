package dei.unipd.it.database;

import dei.unipd.it.resources.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SearchReservationDAO extends AbstractDAO<List<Reservation>> {
	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM lounge.Reservation";

	private final long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int people;
    private final String customer;
    private final int table;


	public SearchReservationDAO(final Connection con, long id, LocalDateTime startTime, LocalDateTime endTime, int people, String customer, int table) {
		super(con);
		this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.people = people;
        this.customer = customer;
        this.table = table;
	}

	@Override
	public final void doAccess() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		// the results of the search
		final List<Reservation> reservations = new ArrayList<>();
		final List<String> clauses= new ArrayList<>();


		if(id>=0){ clauses.add(" lounge.Reservation.id = ? "); }
		if(startTime != null ){ clauses.add(" lounge.Reservation.start_time = ? "); }
		if(endTime != null ){ clauses.add(" lounge.Reservation.end_time = ? "); }
		if(people>=0){ clauses.add(" lounge.Reservation.n_people = ? "); }
		if(customer != null && !customer.isEmpty()){ clauses.add(" lounge.Reservation.user_customerID = ? "); }
		if(table>=0){ clauses.add(" lounge.Reservation.tableNumber = ? "); }

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

			if(id>=0) {
				pstmt.setLong(clauseCount, id);
				clauseCount += 1;
			}
			if(startTime!=null){
				pstmt.setObject(clauseCount, startTime);
				clauseCount+=1;
			}
			if(endTime!=null){
				pstmt.setObject(clauseCount, endTime);
				clauseCount+=1;
			}
			if(people>=0) {
				pstmt.setInt(clauseCount, people);
				clauseCount += 1;
			}
			if(customer!=null) {
				pstmt.setString(clauseCount, customer);
				clauseCount += 1;
			}
			if(table>=0) {
				pstmt.setInt(clauseCount, table);
				clauseCount += 1;
			}




			rs = pstmt.executeQuery();

			while (rs.next()) {
				reservations.add(new Reservation(
						rs.getInt("id"),
						rs.getObject("start_time", LocalDateTime.class),
						rs.getObject("end_time", LocalDateTime.class),
						rs.getInt("n_people"),
						rs.getString("user_customerID"),
						rs.getInt("tableNumber")
				));			}

			LOGGER.info("Reservation(s) successfully listed.");
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

		}

		this.outputParam = reservations;
	}
}
