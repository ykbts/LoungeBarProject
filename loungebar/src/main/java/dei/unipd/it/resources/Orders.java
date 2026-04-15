package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class Orders extends AbstractResource {

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

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("reservation");

        jg.writeStartObject();

        jg.writeNumberField("id", id);
        jg.writeStringField("date-time", MAPPER.writeValueAsString(dateTime));
        jg.writeStringField("endTime", MAPPER.writeValueAsString(endTime));
        jg.writeNumberField("people", people);
        jg.writeStringField("customer", customer);
        jg.writeNumberField("table", table);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }
}
