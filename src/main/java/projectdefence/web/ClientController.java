package projectdefence.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.MeasurementByUserNameViewModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.service.MeasurementService;
import projectdefence.service.TreatmentService;
import projectdefence.service.UserService;

import java.util.List;

@Controller

@RequestMapping("/client")
public class ClientController {
    private final UserService userService;
    private final TreatmentService treatmentService;
    private final MeasurementService measurementService;


    public ClientController(UserService userService,
                            TreatmentService treatmentService,
                            MeasurementService measurementService) {

        this.userService = userService;
        this.treatmentService = treatmentService;
        this.measurementService = measurementService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String allClient(@RequestParam("username") String username, Model model) {

        List<UserWrapInfoViewModel> clients = this.userService.findAllUsersByKinesiotherapist(username);
        model.addAttribute("size", clients.size());
        model.addAttribute("viewAllClients", clients);

        return "view_all_clients";
    }

    @GetMapping("/info/{username}")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String profile(@PathVariable(name = "username") String username, Model model) {

        model.addAttribute("username", username);
        List<TreatmentViewModel> allTreatmentsByUsername = this.treatmentService.findAllTreatmentsByUsername(username);
        if (allTreatmentsByUsername.size() == 0) {
            model.addAttribute("noTreatment", true);
        } else {
            model.addAttribute("allTreatments", allTreatmentsByUsername);
        }
        List<MeasurementByUserNameViewModel> allMeasurementsByUsername = this.measurementService.findAllMeasurementsByUsername(username);

        if (allMeasurementsByUsername.size() == 0) {
            model.addAttribute("noMeasurement", true);
        } else {
            model.addAttribute("allMeasurements", allMeasurementsByUsername);
        }
        return "info_by_username";
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public String delete(@PathVariable(name = "id") String id) {


        this.measurementService.deleteById(id);

        this.treatmentService.deleteById(id);
        return "redirect:/home";
    }
}
