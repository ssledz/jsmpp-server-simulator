package pl.softech.smpp;

/**
 * Created by ssledz on 10.12.15.
 */
public class ShortMessage {

    private String systemId;

    private String systemType;

    private String address;

    private String body;

    protected ShortMessage() {
    }

    public ShortMessage(Builder builder) {
        systemId = builder.systemId;
        systemType = builder.systemType;
        address = builder.address;
        body = builder.body;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getSystemType() {
        return systemType;
    }

    public class Builder {

        private String systemId;

        private String systemType;

        private String address;

        private String body;

        public Builder withSystemId(String systemId) {
            this.systemId = systemId;
            return this;
        }

        public Builder withSystemType(String systemType) {
            this.systemType = systemType;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

    }
}
