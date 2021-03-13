package projectdefence.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import projectdefence.service.UserService;


@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(@AuthenticationPrincipal UserDetails principal) {
        ModelAndView mav = new ModelAndView("home");
        String name = principal.getUsername();
        String imageUrl = this.userService.findImageByUsername(principal.getUsername());
        mav.addObject("user", name);
        mav.addObject("image", imageUrl);
        return mav;
    }
}
