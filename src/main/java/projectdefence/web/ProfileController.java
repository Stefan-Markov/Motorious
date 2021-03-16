package projectdefence.web;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.service.UserService;

@RestController
@RequestMapping("/profile")

public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public UserWrapInfoViewModel profileUser(@PathVariable(name = "username") String username) {

        return this.userService.findProfileByUserName(username);

    }
}
