package pl.softech.smpp;

import org.jsmpp.util.MessageId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by ssledz on 25.01.16.
 */
@Repository
public class ShortMessageRepository {

    private final Map<MessageId, ShortMessage> messages = new ConcurrentHashMap<>();

    public ShortMessage save(ShortMessage shortMessage) {
        return shortMessage;
    }

    public List<ShortMessage> findAll() {
        return messages.values()
                .stream()
                .sorted((l, r) -> l.getMessageId().getValue().compareTo(r.getMessageId().getValue()))
                .collect(Collectors.toList());
    }


}
