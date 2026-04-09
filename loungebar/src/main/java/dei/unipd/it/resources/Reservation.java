package dei.unipd.it.resources;

import java.time.LocalDateTime;

public class Reservation {

    private final long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int people;
    private final String customer;
    private final int table;


    public Reservation(long id, LocalDateTime startTime, LocalDateTime endTime, int people, String customer, int table) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.people = people;
        this.customer = customer;
        this.table = table;
    }

    public long getId() {
        return id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getPeople() {
        return people;
    }
    public String getCustomer() {
        return customer;
    }
    public int getTable() {
        return table;
    }
}
