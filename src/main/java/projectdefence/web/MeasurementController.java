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
import projectdefence.models.binding.MeasurementAddBindingModel;
import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.viewModels.MeasurementViewModel;
import projectdefence.service.MeasurementService;
import projectdefence.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;

    public MeasurementController(UserService userService, ModelMapper modelMapper, MeasurementService measurementService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String addMeasurement(@AuthenticationPrincipal UserDetails principal, Model model) {

        String name = principal.getUsername();
        model.addAttribute("name", name);

        if (!model.containsAttribute("measurementAddBindingModel")) {
            model.addAttribute("measurementAddBindingModel", new MeasurementAddBindingModel());
            model.addAttribute("userFound", false);

        }
        return "measurement_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String addMeasurementConfirm(@RequestParam(name = "nameKt") String nameKt, @Valid @ModelAttribute MeasurementAddBindingModel measurementAddBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("measurementAddBindingModel", measurementAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.measurementAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }

        boolean userFound = this.userService.findByUsername(measurementAddBindingModel.getUsername());
        if (!userFound) {
            redirectAttributes.addFlashAttribute("measurementAddBindingModel", measurementAddBindingModel);
            redirectAttributes.addFlashAttribute("userFound", true);
            return "redirect:add";
        }

        this.measurementService.addMeasurement(this.modelMapper.map(measurementAddBindingModel, MeasurementAddServiceModel.class),
                measurementAddBindingModel.getUsername(), nameKt);

        return "redirect:/home";
    }


    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String checkMeasurement(@RequestParam("username") String username, Model model) {

        List<MeasurementViewModel> allMeasurementsByUsername = this.measurementService.findAllMeasurementsByUsername(username);

        if (allMeasurementsByUsername.size() == 0) {
            model.addAttribute("no", true);
        } else {
            model.addAttribute("allMeasurements", allMeasurementsByUsername);
        }
        return "measurement-check";
    }
}
