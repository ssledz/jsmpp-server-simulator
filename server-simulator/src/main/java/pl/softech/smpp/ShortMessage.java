package pl.softech.smpp;

import org.jsmpp.util.MessageId;

/**
 * Created by ssledz on 25.01.16.
 */
public class ShortMessage {

    private MessageId messageId;

    private String sourceMsisdn;

    private String destinationMsisdn;

    private String body;

    public ShortMessage(MessageId messageId, String body) {
        this.messageId = messageId;
        this.body = body;
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public String getBody() {
        return body;
    }

    public String getDestinationMsisdn() {
        return destinationMsisdn;
    }

    public String getSourceMsisdn() {
        return sourceMsisdn;
    }
}
