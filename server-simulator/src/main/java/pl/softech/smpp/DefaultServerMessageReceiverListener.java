package pl.softech.smpp;

import org.jsmpp.bean.*;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.*;
import org.jsmpp.util.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;

import static pl.softech.smpp.Specifications.deliveryReceiptSpecification;

public class DefaultServerMessageReceiverListener implements ServerMessageReceiverListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerMessageReceiverListener.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ShortMessageRepository shortMessageRepository;

    @Autowired
    private ShortMessageFactory shortMessageFactory;

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageId onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession source) throws ProcessRequestException {

        ShortMessage message = shortMessageFactory.createFrom(submitSm);

        message = shortMessageRepository.save(message);

        LOGGER.info("Receiving submit_sm '{}', and return message id {}", message.getBody(),
                message.getMessageId());

        if (deliveryReceiptSpecification().isSatisfiedBy(submitSm)) {
            executorService.execute(new SmppDeliveryReceiptTask(source, submitSm, message.getMessageId()));
        }

        return message.getMessageId();
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
