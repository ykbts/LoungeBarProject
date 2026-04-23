package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a service review provided by a customer for a specific waiter.
 */
public class Review extends AbstractResource {

    private final String waiterID;
    private final String customerID;
    private final int score;
    private final String comment;

    /**
     * @param waiterID ID of the waiter being reviewed
     * @param customerID ID of the customer providing feedback
     * @param score numerical rating (typically 1-5)
     * @param comment optional text feedback
     */
    public Review(String waiterID, String customerID, int score, String comment) {
        this.waiterID = waiterID;
        this.customerID = customerID;
        this.score = score;
        this.comment = comment;
    }

    /**
     * @param waiterID ID of the waiter being reviewed
     * @param customerID ID of the customer providing feedback
     * @param score numerical rating
     */
    public Review(String waiterID, String customerID, int score) {
        this.waiterID = waiterID;
        this.customerID = customerID;
        this.score = score;
        this.comment = null;
    }

    public String getWaiterID() {
        return waiterID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("review");

        jg.writeStartObject();

        jg.writeStringField("waiter", waiterID);
        jg.writeStringField("customer", customerID);
        jg.writeNumberField("score", score);
        jg.writeStringField("comment", comment);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Parses a Review from a JSON input stream.
     *
     * @param in the input stream
     * @return a new Review instance
     * @throws IOException if parsing fails
     */
    public static Review fromJSON(InputStream in) throws IOException {

        String jWaiter = null;
        String jCustomer = null;
        int jScore = -1;
        String jComment = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"review".equals(jp.getCurrentName())) {

                if (jp.nextToken() == null) {
                    LOGGER.error("No REVIEW object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no REVIEW object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {

                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                    switch (jp.getCurrentName()) {
                        case "waiter":
                            jp.nextToken();
                            jWaiter = jp.getText();
                            break;
                        case "customer":
                            jp.nextToken();
                            jCustomer = jp.getText();
                            break;
                        case "score":
                            jp.nextToken();
                            jScore = jp.getIntValue();
                            break;
                        case "comment":
                            jp.nextToken();
                            jComment = jp.getText();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a REVIEW object from JSON.", e);
            throw e;
        }

        return new Review(jWaiter, jCustomer, jScore, jComment);
    }
}
