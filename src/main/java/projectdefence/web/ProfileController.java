package projectdefence.web;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.EditProfileBindingModel;
import projectdefence.models.binding.UserRegisterBindingModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/edit/{username}")
    @PreAuthorize("#username == authentication.name")
    public String profileUser(@PathVariable(name = "username") String username, Model model) {

        UserWrapInfoViewModel profileByUserName = this.userService.findProfileByUserName(username);
        model.addAttribute("userProfile", profileByUserName);
        if (!model.containsAttribute("editProfileBindingModel")) {
            model.addAttribute("editProfileBindingModel", new EditProfileBindingModel());
        }

        return "/profile";
    }

    @PostMapping("/edit/{username}")
    @PreAuthorize("#username ==  authentication.name")
    public String
    profileUserConfirm(@PathVariable(name = "username") String username,
                       @Valid @ModelAttribute EditProfileBindingModel editProfileBindingModel,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editProfileBindingModel", editProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editProfileBindingModel",
                    bindingResult);

            return "redirect:/profile/edit/" + username;
        }
        return "redirect:/home";
    }
}
