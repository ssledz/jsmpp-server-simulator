package pl.softech.smpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ssledz on 10.12.15.
 */
@RestController
@RequestMapping(value = "/ctl")
public class SmppServerRestController {

    @Autowired
    private SmppServerSimulator serverSimulator;

    @RequestMapping(value = "/submit-sm", method = RequestMethod.POST, consumes = "application/json;charset=utf-8")
    public void submitSm(@RequestBody ShortMessageDto message) throws Exception {
        serverSimulator.sendMessage(message);
    }

    @RequestMapping(value = "/short-message", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<ShortMessageDto> submitSm(@RequestBody ShortMessageDto message) {
        serverSimulator.sendMessage(message);
    }

}
