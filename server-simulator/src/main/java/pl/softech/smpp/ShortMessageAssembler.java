package pl.softech.smpp;

/**
 * Created by ssledz on 25.01.16.
 */
public class ShortMessageAssembler {

    public ShortMessageDto fromModelToView(ShortMessage message) {
        return new ShortMessageDto(new ShortMessageDto.Builder()
                .withDestMsisdn(message.getDestinationMsisdn())
//                .withBody()
        );
    }

}
