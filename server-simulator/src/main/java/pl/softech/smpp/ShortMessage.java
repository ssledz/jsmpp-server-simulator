package pl.softech.smpp;

/**
 * Created by ssledz on 10.12.15.
 */
public class ShortMessage {

    private String address;
    private String body;

    public ShortMessage(String address, String body) {
        this.address = address;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }
}
