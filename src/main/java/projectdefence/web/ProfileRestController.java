package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.security.rolesAuth.IsKinesitherapist;
import projectdefence.security.rolesAuth.IsProfileUser;
import projectdefence.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/profile-rest")
public class ProfileRestController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/edit/{username}")
    @IsProfileUser
    public ResponseEntity<UserWrapInfoViewModel> editProfile(@PathVariable String username) {

        UserWrapInfoViewModel profileByUsername = this.userService.findProfileByUserName(username);

        return ResponseEntity.ok(profileByUsername);
    }

    @GetMapping("/users/{username}")
    @IsKinesitherapist
    public ResponseEntity<List<UserWrapInfoViewModel>> findUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(this.userService.findByGivenUsername(username));
    }
}
