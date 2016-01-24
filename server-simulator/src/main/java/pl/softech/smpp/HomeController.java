package pl.softech.smpp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("defaultSystemId", "si");
        model.addAttribute("defaultSystemType", "st");
        model.addAttribute("defaultAddress", "123 4567 234");
        model.addAttribute("defaultBody", "Sample Message");
        return "home";
    }

}
