package pl.softech.smpp;

/**
 * Created by ssledz on 10.12.15.
 */
public class SubmitShortMessageResponse {

    private String messageId;

    public SubmitShortMessageResponse(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
