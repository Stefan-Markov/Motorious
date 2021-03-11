package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.UserChangeRoleBindingModel;
import projectdefence.models.binding.UserLoginBindingModel;
import projectdefence.models.binding.UserRegisterBindingModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
            model.addAttribute("isExists", false);
            model.addAttribute("notSame", false);
        }
        return "registration";
    }


    @PostMapping("/registration")
    public String registerConfirm(@Valid @ModelAttribute UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "redirect:registration";
        }

        if (!userRegisterBindingModel.isSamePasswords()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("notSame", true);
            return "redirect:registration";
        }

        boolean isSaved = userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        if (!isSaved) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isExists", true);
            return "redirect:registration";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("notFound", false);
        }

        return "login";
    }
    
    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                            String username) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("notFound", true);
        modelAndView.addObject("username", username);

        modelAndView.setViewName("/login");

        return modelAndView;
    }

    @GetMapping("delete-user")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String deleteUser(Model model) {
        if (!model.containsAttribute("userChangeRoleBindingModel")) {
            model.addAttribute("userChangeRoleBindingModel", new UserChangeRoleBindingModel());
        }

        return "delete-user";
    }

    @PostMapping("delete-user")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String deleteUserConfirm(@Valid @ModelAttribute UserChangeRoleBindingModel userChangeRoleBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangeRoleBindingModel",
                    bindingResult);
            return "redirect:delete-user";
        }

        String username = userChangeRoleBindingModel.getUsername();
        this.userService.deleteUserByUsername(username);
        return "redirect:/home";
    }

    @GetMapping("all-users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String allUsers(Model model) {

        model.addAttribute("viewAllKts", this.userService.findByTitleKT());
        model.addAttribute("viewAllClients", this.userService.findByTitleClient());
        model.addAttribute("allUsers", this.userService.findAllUsers());

        return "view_all_users";
    }
}
