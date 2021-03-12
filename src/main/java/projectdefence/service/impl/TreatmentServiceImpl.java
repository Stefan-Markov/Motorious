package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectdefence.models.binding.TreatmentAddBindingModel;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.MeasurementByUserNameViewModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.repositories.TreatmentRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.TreatmentService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final ModelMapper modelMapper;
    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;

    public TreatmentServiceImpl(ModelMapper modelMapper, TreatmentRepository treatmentRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.treatmentRepository = treatmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addTreatment(TreatmentAddBindingModel treatmentAddBindingModel, String username, String nameKt) {

        User user = this.userRepository.findByUsername(username);
        Treatment treatment = this.modelMapper.map(treatmentAddBindingModel, Treatment.class);
        treatment.setUser(user);
        treatment.setDateAdded(LocalDate.now());
        treatment.setCreatedBy(nameKt);

        this.treatmentRepository.save(treatment);
    }

    @Override
    public List<TreatmentViewModel> findAllTreatmentsByUsername(String username) {


        User user = this.userRepository.findByUsername(username);

        return user.getTreatments().stream().map(t -> this.modelMapper.map(t, TreatmentViewModel.class))
                .sorted(Comparator.comparing(TreatmentViewModel::getDateAdded).reversed())
                .collect(Collectors.toList());
    }
}
