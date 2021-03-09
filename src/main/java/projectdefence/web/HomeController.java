package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.UserViewModel;
import projectdefence.service.impl.MotoriousUserDetailsService;

@Controller
public class HomeController {

    private final ModelMapper modelMapper;


    public HomeController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(@ModelAttribute UserViewModel userViewModel, Model model) {
        if (!model.containsAttribute("userViewModel")) {

            model.addAttribute("userViewModel", this.modelMapper.map(userViewModel, User.class));
        }
        return "home";
    }
}
