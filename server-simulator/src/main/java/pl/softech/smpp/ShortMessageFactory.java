package pl.softech.smpp;

import org.jsmpp.bean.SubmitSm;
import org.jsmpp.util.MessageIDGenerator;
import org.jsmpp.util.MessageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ssledz on 25.01.16.
 */
@Component
public class ShortMessageFactory {

    @Autowired
    private MessageIDGenerator messageIdGenerator;

    public ShortMessage createFrom(SubmitSm sm) {
        MessageId messageId = messageIdGenerator.newMessageId();
        return new ShortMessage(messageId,  new String(sm.getShortMessage()));
    }

}
