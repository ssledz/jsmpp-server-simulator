package pl.softech.smpp;

import org.jsmpp.bean.SubmitSm;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.util.MessageId;

public class SmppDeliveryReceiptTask implements Runnable {

    private final SMPPServerSession session;

    private final MessageId messageId;

    public SmppDeliveryReceiptTask(SMPPServerSession session, SubmitSm submitSm, MessageId messageId) {
        this.session = session;
        this.messageId = messageId;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
