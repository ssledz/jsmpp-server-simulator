package pl.softech.smpp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.jsmpp.PDUStringException;
import org.jsmpp.SMPPConstant;
import org.jsmpp.bean.InterfaceVersion;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SmppBindTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmppBindTask.class);

    private final SMPPServerSession serverSession;

    @Autowired
    private ServerSessionRepository serverSessionRepository;

    public SmppBindTask(SMPPServerSession serverSession) {
        this.serverSession = serverSession;
    }

    @Override
    public void run() {
        try {
            BindRequest bindRequest = serverSession.waitForBind(1000);
            LOGGER.info("Accepting bind for session {}, interface version {}", serverSession.getSessionId(),
                    bindRequest.getInterfaceVersion());
            try {
                bindRequest.accept("sys", InterfaceVersion.IF_34);
            } catch (PDUStringException e) {
                LOGGER.error("Invalid system id", e);
                bindRequest.reject(SMPPConstant.STAT_ESME_RSYSERR);
            }

            serverSessionRepository.bind(bindRequest, serverSession);

        } catch (IllegalStateException e) {
            LOGGER.error("System error", e);
        } catch (TimeoutException e) {
            LOGGER.warn("Wait for bind has reach timeout", e);
        } catch (IOException e) {
            LOGGER.error("Failed accepting bind request for session {}", serverSession.getSessionId());
        }
    }

}
