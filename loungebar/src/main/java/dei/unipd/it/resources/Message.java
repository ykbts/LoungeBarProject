package dei.unipd.it.resources;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class Message extends AbstractResource {

    private final String message;
    private final String errorCode;
    private final String errorDetails;
    private final boolean isError;

    public Message(final String message, final String errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }

    public Message(final String message) {
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public boolean isError() {
        return isError;
    }

    @Override
    protected void writeJSON(OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeStringField("message", message);

        jg.writeStartObject();

        if (errorCode != null)
            jg.writeStringField("error-code", errorCode);

        if (errorDetails != null)
            jg.writeStringField("error-details", errorDetails);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }
}
