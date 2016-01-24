package pl.softech.smpp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("pages", Arrays.asList("submit-sm"));
        return "home";
    }

    @RequestMapping(value = "/submit-sm")
    public String submitSmHtml() {
        return "submit-sm";
    }

}
