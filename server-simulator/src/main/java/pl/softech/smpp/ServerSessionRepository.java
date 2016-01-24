package pl.softech.smpp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jsmpp.bean.BindType;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;
import org.springframework.stereotype.Component;

@Component
public final class ServerSessionRepository {

    private final Map<SessionKey, SMPPServerSession> bind2session = new ConcurrentHashMap<SessionKey, SMPPServerSession>();

    public void bind(BindRequest request, SMPPServerSession session) {
        bind2session.put(new SessionKey(request), session);
    }

    public SMPPServerSession findSessionByIdentifier(BindType bindType, String systemId, String systemType) {
        SessionKey key = new SessionKey(bindType, systemId, systemType);
        return bind2session.get(key);
    }

    private static final class SessionKey {

        private final BindType bindType;

        private final String systemId;

        private final String systemType;

        private SessionKey(BindRequest request) {
            this(request.getBindType(), request.getSystemId(), request.getSystemType());
        }

        private SessionKey(BindType bindType, String systemId, String systemType) {
            this.systemId = systemId;
            this.systemType = systemType;
            this.bindType = bindType;
        }

        @Override
        public int hashCode() {

            return new HashCodeBuilder()//
                    .append(systemId)//
                    .append(systemType)//
                    .append(bindType)//
                    .build();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof SessionKey)) {
                return false;
            }

            SessionKey rhs = (SessionKey) obj;
            return new EqualsBuilder()//
                    .append(systemId, rhs.systemId)//
                    .append(systemType, rhs.systemType)//
                    .append(bindType, rhs.bindType)//
                    .build();
        }

    }

}
