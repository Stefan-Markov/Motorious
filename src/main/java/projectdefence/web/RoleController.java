package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.UserChangeRoleBindingModel;
import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public RoleController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/role-change")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeRole(Model model) {
        if (!model.containsAttribute("userChangeRoleBindingModel")) {
            model.addAttribute("userFound", false);
            model.addAttribute("userChangeRoleBindingModel", new UserChangeRoleBindingModel());
        }

        return "role_change";
    }

    @PostMapping("/role-change")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeRoleConfirm(@RequestParam(name = "role") String role,
                                    @Valid @ModelAttribute UserChangeRoleBindingModel userChangeRoleBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangeRoleBindingModel",
                    bindingResult);
            return "redirect:role-change";
        }
        boolean userFound = this.userService.findByUsername(userChangeRoleBindingModel.getUsername());

        if (!userFound) {
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);
            redirectAttributes.addFlashAttribute("userFound", true);
            return "redirect:role-change";
        }

        this.userService.changeRole(this.modelMapper
                .map(userChangeRoleBindingModel, UserServiceChangeRoleModel.class), role);
        return "redirect:/home";
    }

    @GetMapping("/role-downgrade")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String roleDowngrade(Model model) {
        if (!model.containsAttribute("userChangeRoleBindingModel")) {
            model.addAttribute("userFound", false);
            model.addAttribute("userChangeRoleBindingModel", new UserChangeRoleBindingModel());
        }

        return "role_downgrade";
    }

    @PostMapping("/role-downgrade")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String roleDowngradeConfirm(@RequestParam(name = "role") String role,
                                       @Valid @ModelAttribute UserChangeRoleBindingModel userChangeRoleBindingModel,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangeRoleBindingModel",
                    bindingResult);
            return "redirect:role-downgrade";
        }

        boolean userFound = this.userService.findByUsername(userChangeRoleBindingModel.getUsername());

        if (!userFound) {
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);
            redirectAttributes.addFlashAttribute("userFound", true);
            return "redirect:role-downgrade";
        }


        this.userService.downgradeRole(this.modelMapper
                .map(userChangeRoleBindingModel, UserServiceChangeRoleModel.class), role);
        return "redirect:/home";

    }
}

