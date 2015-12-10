package pl.softech.smpp;

import static pl.softech.smpp.Specifications.deliveryReceiptSpecification;

import java.util.concurrent.ExecutorService;

import org.jsmpp.bean.CancelSm;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.QuerySm;
import org.jsmpp.bean.ReplaceSm;
import org.jsmpp.bean.SubmitMulti;
import org.jsmpp.bean.SubmitMultiResult;
import org.jsmpp.bean.SubmitSm;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.QuerySmResult;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.ServerMessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.MessageIDGenerator;
import org.jsmpp.util.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultServerMessageReceiverListener implements ServerMessageReceiverListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerMessageReceiverListener.class);

    @Autowired
    private MessageIDGenerator messageIdGenerator;

    @Autowired
    private ExecutorService executorService;

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageId onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession source) throws ProcessRequestException {
        MessageId messageId = messageIdGenerator.newMessageId();

        LOGGER.info("Receiving submit_sm '{}', and return message id {}", new String(submitSm.getShortMessage()),
                messageId);

        if (deliveryReceiptSpecification().isSatisfiedBy(submitSm)) {
            executorService.execute(new SmppDeliveryReceiptTask(source, submitSm, messageId));
        }

        return messageId;

    }

    @Override
    public SubmitMultiResult onAcceptSubmitMulti(SubmitMulti submitMulti, SMPPServerSession source)
            throws ProcessRequestException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QuerySmResult onAcceptQuerySm(QuerySm querySm, SMPPServerSession source) throws ProcessRequestException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onAcceptReplaceSm(ReplaceSm replaceSm, SMPPServerSession source) throws ProcessRequestException {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAcceptCancelSm(CancelSm cancelSm, SMPPServerSession source) throws ProcessRequestException {
        // TODO Auto-generated method stub

    }

}
