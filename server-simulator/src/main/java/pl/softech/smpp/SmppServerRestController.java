package pl.softech.smpp;

import org.springframework.web.bind.annotation.*;

/**
 * Created by ssledz on 10.12.15.
 */
@RestController
@RequestMapping(value = "/ctl")
public class SmppServerRestController {

    @ResponseBody
    @RequestMapping(value = "/submitSm", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public SubmitShortMessageResponse submitSm(@RequestBody ShortMessage message) {
        return new SubmitShortMessageResponse("1234: " + message.getBody());
    }


}
