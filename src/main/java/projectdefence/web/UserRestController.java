package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.security.rolesAuth.IsRoot;
import projectdefence.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserRestController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/all-users")
    @IsRoot
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<UserWrapInfoViewModel> allUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {

        return userService.getUsersPage(page, limit);
    }

}
