package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * Represents a customer order in the lounge bar.
 */
public class Orders extends AbstractResource {

    private final int id;
    private final LocalDateTime dateTime;
    private final OrderState status;
    private final PayMethod payMethod;
    private final String waiterID;
    private final int tableID;

    /**
     * @param id unique order identifier
     * @param dateTime timestamp of the order
     * @param status current {@link OrderState}
     * @param payMethod chosen {@link PayMethod}
     * @param waiterID ID of the waiter handling the order
     * @param tableID ID of the assigned table
     */
    public Orders(int id, LocalDateTime dateTime, OrderState status, PayMethod payMethod, String waiterID, int tableID) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.payMethod = payMethod;
        this.waiterID = waiterID;
        this.tableID = tableID;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public OrderState getStatus() {
        return status;
    }

    public PayMethod getPayMethod() {
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
        jg.writeFieldName("order");
        jg.writeStartObject();
        jg.writeNumberField("id", id);
        jg.writeStringField("date_time", dateTime.format(FORMATTER));
        jg.writeStringField("status", status.toString());
        jg.writeStringField("pay-method", payMethod.toString());
        jg.writeStringField("waiter", waiterID);
        jg.writeNumberField("table", tableID);
        jg.writeEndObject();
        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates an Orders object from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Orders instance
     * @throws IOException if parsing fails
     */
    public static Orders fromJSON(InputStream in) throws IOException {
        int jId = -1;
        LocalDateTime jDateTime = null;
        OrderState jStatus = null;
        PayMethod jPayMethod = null;
        String jWaiter = null;
        int jTable = -1;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"order".equals(jp.getCurrentName())) {
                if (jp.nextToken() == null) {
                    throw new EOFException("Unable to parse JSON: no ORDER object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getIntValue();
                            break;
                        case "date-time":
                            jp.nextToken();
                            jDateTime = MAPPER.readValue(jp, LocalDateTime.class);
                            break;
                        case "status":
                            jp.nextToken();
                            jStatus = OrderState.valueOf(jp.getText());
                            break;
                        case "pay-method":
                            jp.nextToken();
                            jPayMethod = PayMethod.valueOf(jp.getText());
                            break;
                        case "waiter":
                            jp.nextToken();
                            jWaiter = jp.getText();
                            break;
                        case "table":
                            jp.nextToken();
                            jTable = jp.getIntValue();
                            break;
                    }
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("Unable to parse a RESERVATION object from JSON.", e);
            throw e;
        }

        return new Orders(jId, jDateTime, jStatus, jPayMethod, jWaiter, jTable);
    }
}