package pl.softech.smpp;

import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.SubmitSm;

public class Specifications {

    private Specifications() {
    }

    public static Specification<SubmitSm> deliveryReceiptSpecification() {
        return submitSm -> SMSCDeliveryReceipt.SUCCESS.containedIn(submitSm.getRegisteredDelivery())
                || SMSCDeliveryReceipt.SUCCESS_FAILURE.containedIn(submitSm.getRegisteredDelivery());
    }

}
