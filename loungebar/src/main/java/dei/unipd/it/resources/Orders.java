package dei.unipd.it.resources;

import java.time.LocalDateTime;

public class Orders {

    private final long id;
    private final LocalDateTime dateTime;
    private final String status;
    private final String payMethod;
    private final String waiterID;
    private final int tableID;

    public Orders(long id, LocalDateTime dateTime, String status, String payMethod, String waiterID, int tableID) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.payMethod = payMethod;
        this.waiterID = waiterID;
        this.tableID = tableID;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public String getWaiterID() {
        return waiterID;
    }

    public int getTableID() {
        return tableID;
    }
}
