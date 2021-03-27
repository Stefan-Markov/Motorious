package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.TreatmentAddBindingModel;
import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.service.TreatmentService;
import projectdefence.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TreatmentService treatmentService;

    public TreatmentController(UserService userService, ModelMapper modelMapper, TreatmentService treatmentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String addTreatment(@AuthenticationPrincipal UserDetails principal, Model model) {

        String name = principal.getUsername();
        model.addAttribute("name", name);

        if (!model.containsAttribute("treatmentAddBindingModel")) {
            model.addAttribute("treatmentAddBindingModel", new TreatmentAddBindingModel());
            model.addAttribute("userFound", false);
        }

        return "treatment_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String addTreatmentConfirm(@RequestParam(name = "nameKt") String nameKt, @Valid @ModelAttribute TreatmentAddBindingModel treatmentAddBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("treatmentAddBindingModel", treatmentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.treatmentAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }

        boolean userFound = this.userService.findByUsername(treatmentAddBindingModel.getUsername());
        if (!userFound) {
            redirectAttributes.addFlashAttribute("treatmentAddBindingModel", treatmentAddBindingModel);
            redirectAttributes.addFlashAttribute("userFound", true);
            return "redirect:add";
        }

        this.treatmentService.addTreatment(this.modelMapper.map(treatmentAddBindingModel, TreatmentAddServiceModel.class),
                treatmentAddBindingModel.getUsername(), nameKt);

        return "redirect:/home";
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String checkTreatment(@RequestParam("username") String username, Model model) {

        List<TreatmentViewModel> allTreatmentsByUsername = this.treatmentService.findAllTreatmentsByUsername(username);
        if (allTreatmentsByUsername.size() == 0) {
            model.addAttribute("no", true);
        } else {
            model.addAttribute("allTreatments", allTreatmentsByUsername);
        }
        return "treatment_check";
    }
}
