package pl.softech.smpp;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPServerSessionListener;
import org.jsmpp.session.ServerMessageReceiverListener;
import org.jsmpp.session.ServerResponseDeliveryListener;
import org.jsmpp.session.connection.socket.ServerSocketConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SmppServerSimulator implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmppServerSimulator.class);

    @Autowired
    private ExecutorService executorService;

    @Value("${smpp.server.port}")
    private int port;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private AtomicBoolean isShutdown = new AtomicBoolean(true);

    private ServerMessageReceiverListener messageReceiverListener;

    private ServerResponseDeliveryListener responseDeliveryListener;

    private SMPPServerSessionListener sessionListener;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition shutdown = lock.newCondition();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        messageReceiverListener = new DefaultServerMessageReceiverListener();
        responseDeliveryListener = new DefaultServerResponseDelivery();

        asList(messageReceiverListener, responseDeliveryListener)
                .forEach(listener -> applicationContext.getAutowireCapableBeanFactory().autowireBean(listener));

    }

    @PostConstruct
    public void start() throws IOException {

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
                shutdown.wait();
            }
            executorService.shutdown();
            sessionListener.close();
        } finally {
            lock.unlock();
        }
    }

    private void acceptConnection() throws IOException {
        try {
            SMPPServerSession serverSession = sessionListener.accept();
            LOGGER.info("Accepting connection for session {}", serverSession.getSessionId());
            serverSession.setMessageReceiverListener(messageReceiverListener);
            serverSession.setResponseDeliveryListener(responseDeliveryListener);
            executorService.execute(new SmppBindTask(serverSession));
        } catch (SocketTimeoutException e) {
            LOGGER.debug(e.toString());
        }
    }

}
