package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    public HomeController() {

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal UserDetails principal) {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("user", principal);
        return mav;
    }
}
