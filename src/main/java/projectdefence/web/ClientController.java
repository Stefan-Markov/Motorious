package projectdefence.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final UserService userService;

    public ClientController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String allClient(@RequestParam("username") String username, Model model) {

        List<UserWrapInfoViewModel> clients = this.userService.findAllUsersByKinesiotherapist(username);
        model.addAttribute("size", clients.size());
        model.addAttribute("viewAllClients", clients);

        return "view_all_clients";
    }
}
