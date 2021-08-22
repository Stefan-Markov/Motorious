package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.security.IsKinesitherapist;
import projectdefence.service.TreatmentService;
import projectdefence.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/tr")
public class TreatmentRestController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TreatmentService treatmentService;

    public TreatmentRestController(UserService userService, ModelMapper modelMapper, TreatmentService treatmentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{criteria}")
    @IsKinesitherapist
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TreatmentViewModel> allTreatmentByCriteria(@PathVariable String criteria) {
        return this.treatmentService.findAllByCriteria(criteria);
    }
}
