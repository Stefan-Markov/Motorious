package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.repositories.TreatmentRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.TreatmentService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final ModelMapper modelMapper;
    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;

    public TreatmentServiceImpl(ModelMapper modelMapper, TreatmentRepository treatmentRepository,
                                UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.treatmentRepository = treatmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addTreatment(TreatmentAddServiceModel treatmentAddServiceModel, String username, String nameKt) {

        User user = this.userRepository.findByUsername(username);
        Treatment treatment = this.modelMapper.map(treatmentAddServiceModel, Treatment.class);
        treatment.setUser(user)
                .setDateAdded(LocalDate.now());

        User ktUser = this.userRepository.findByUsername(nameKt);
        treatment.setKtFullName(ktUser.getFirstName() + " " + ktUser.getLastName())
                .setCreatedBy(nameKt);
        this.treatmentRepository.save(treatment);
    }

    @Override
    public List<TreatmentViewModel> findAllTreatmentsByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        return user.getTreatments().stream().map(t -> this.modelMapper.map(t, TreatmentViewModel.class))
                .sorted(Comparator.comparing(TreatmentViewModel::getDateAdded).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        Optional<Treatment> treatment = this.treatmentRepository.findById(id);
        treatment.ifPresent(this.treatmentRepository::delete);
    }

    @Override
    public List<TreatmentViewModel> findAllByCriteria(String criteria) {
        return this.treatmentRepository.findAllByGivenCriteria(criteria)
                .stream()
                .map(t -> this.modelMapper.map(t, TreatmentViewModel.class))
                .sorted(Comparator.comparing(TreatmentViewModel::getDateAdded).reversed())
                .collect(Collectors.toList());
    }
}
