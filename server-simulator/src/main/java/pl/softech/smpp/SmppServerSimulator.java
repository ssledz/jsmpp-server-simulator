package pl.softech.smpp;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPServerSessionListener;
import org.jsmpp.session.ServerMessageReceiverListener;
import org.jsmpp.session.ServerResponseDeliveryListener;
import org.jsmpp.session.connection.socket.ServerSocketConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SmppServerSimulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmppServerSimulator.class);

    @Autowired
    private ExecutorService executorService;

    @Value("${smpp.server.port}")
    private int port;

    @Autowired
    private ServerMessageReceiverListener messageReceiverListener;

    @Autowired
    private ServerResponseDeliveryListener responseDeliveryListener;

    @Autowired
    private ServerSessionRepository serverSessionRepository;

    @Autowired
    private ApplicationContext applicationContext;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private AtomicBoolean isShutdown = new AtomicBoolean(true);

    private SMPPServerSessionListener sessionListener;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition shutdown = lock.newCondition();

    @PostConstruct
    public void start() {

        executorService.execute(() -> {
            try {
                run();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });

    }

    private void run() throws IOException {

        sessionListener = new SMPPServerSessionListener(port, 1000, new ServerSocketConnectionFactory());

        isRunning.set(true);
        isShutdown.set(false);

        LOGGER.info("Listening on port {}", port);

        while (isRunning.get()) {

            try {
                acceptConnection();
            } catch (Exception e) {
                LOGGER.error("Error during accepting connection occurred", e);
            }
        }

        try {
            lock.lock();
            shutdown.signal();
            isShutdown.set(true);
        } finally {
            lock.unlock();
        }

    }

    public void shutdown() throws Exception {

        isRunning.set(false);

        try {
            lock.lock();
            while (isShutdown.get()) {
                shutdown.await();
            }
            executorService.shutdown();
            sessionListener.close();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Sending message encoded in ISO-8859-1
     */
    public void sendMessage(ShortMessage message) throws Exception {

        byte[] shortMessageBytes = message.getBody().getBytes(Charset.forName("ISO-8859-1"));

        LOGGER.info("Getting server session for ({},{})...", message.getSystemId(), message.getSystemType());

        SMPPServerSession session = serverSessionRepository.findSessionByIdentifier(BindType.BIND_RX,
                message.getSystemId(),
                message.getSystemType());

        LOGGER.info("Sending message: '{}' to ({},{}, {})...", message.getBody(), message.getSystemId(),
                message.getSystemType(), message.getAddress());

        session.deliverShortMessage("st", //
                TypeOfNumber.UNKNOWN, //
                NumberingPlanIndicator.UNKNOWN, //
                message.getAddress(), //
                TypeOfNumber.UNKNOWN, //
                NumberingPlanIndicator.UNKNOWN, //
                "1234", //
                new ESMClass(), //
                (byte) 0, //
                (byte) 0, //
                new RegisteredDelivery(), //
                new GeneralDataCoding(Alphabet.ALPHA_LATIN1, MessageClass.CLASS0, false), //
                shortMessageBytes);

        LOGGER.info("Message: '{}' already sent to ({},{})", message.getBody(), message.getSystemId(),
                message.getSystemType(), message.getAddress());
    }

    private void acceptConnection() throws IOException {
        try {
            SMPPServerSession serverSession = sessionListener.accept();
            LOGGER.info("Accepting connection for session {}", serverSession.getSessionId());
            serverSession.setMessageReceiverListener(messageReceiverListener);
            serverSession.setResponseDeliveryListener(responseDeliveryListener);
            executorService.execute(autowire(new SmppBindTask(serverSession)));
        } catch (SocketTimeoutException e) {
            LOGGER.debug(e.toString());
        }
    }

    private <T> T autowire(T bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
        return bean;
    }

}
