package pl.softech.smpp;

/**
 * Created by ssledz on 10.12.15.
 */
public class ShortMessageDto {

    private String systemId;

    private String systemType;

    private String destMsisdn;

    private String body;

    protected ShortMessageDto() {
    }

    public ShortMessageDto(Builder builder) {
        systemId = builder.systemId;
        systemType = builder.systemType;
        destMsisdn = builder.destMsisdn;
        body = builder.body;
    }

    public String getDestMsisdn() {
        return destMsisdn;
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

    public static class Builder {

        private String systemId;

        private String systemType;

        private String destMsisdn;

        private String body;

        public Builder withSystemId(String systemId) {
            this.systemId = systemId;
            return this;
        }

        public Builder withSystemType(String systemType) {
            this.systemType = systemType;
            return this;
        }

        public Builder withDestMsisdn(String destMsisdn) {
            this.destMsisdn = destMsisdn;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

    }
}
