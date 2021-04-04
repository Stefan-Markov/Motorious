package projectdefence.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.EditProfileBindingModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/edit/{username}")
    @PreAuthorize("#username == authentication.name")
    public String profileUser(@PathVariable(name = "username") String username, Model model) {

        UserWrapInfoViewModel profileByUsername = this.userService.findProfileByUserName(username);
        model.addAttribute("userProfile", profileByUsername);
        if (!model.containsAttribute("editProfileBindingModel")) {
            model.addAttribute("isFallback", false);
            model.addAttribute("editProfileBindingModel", new EditProfileBindingModel());
        }

        return "/profile";
    }

    @PostMapping("/edit/{username}")
    @PreAuthorize("#username ==  authentication.name")
    public String profileUserConfirm(@PathVariable(name = "username") String username,
                                     @Valid @ModelAttribute EditProfileBindingModel editProfileBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editProfileBindingModel", editProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editProfileBindingModel",
                    bindingResult);

            return "redirect:/profile/edit/" + username;
        }

        boolean isFallback = this.userService.editProfile(this.modelMapper.map(editProfileBindingModel, UserServiceModel.class), username);

        if (!isFallback) {
            redirectAttributes.addFlashAttribute("editProfileBindingModel", editProfileBindingModel);
            redirectAttributes.addFlashAttribute("isFallback", true);
            return "redirect:/profile/edit/" + username;
        }
        return "redirect:/home";
    }
}
