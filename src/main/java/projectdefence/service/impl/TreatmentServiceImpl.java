package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.repositories.TreatmentRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.EmailService;
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
    private final EmailService emailService;

    public TreatmentServiceImpl(ModelMapper modelMapper, TreatmentRepository treatmentRepository,
                                UserRepository userRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.treatmentRepository = treatmentRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
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

        String subject = String.format("New treatment for %s by: kinesitherapist %s", user.getUsername(), treatment.getKtFullName());

        String content = String.format("Treatment:<br>Content: %s<br>Date added: %s<br>Visits: " +
                        "%s<br>Goal: %s<br>Duration: %s<br>By kinesitherapist: %s<br>You can check this treatment at: <a href=\"http://localhost:8080/treatment/check/?username=%s\">Click here <a/>" +
                        "<br>If you have any question, don't hesitate to contact us.<br> " +
                        " mail: motorious.headquarters@gmail.com",
                treatment.getContent(), treatment.getDateAdded(),
                treatment.getVisits(), treatment.getGoal(),
                treatment.getDuration(),
                treatment.getKtFullName(), user.getUsername());


        this.emailService.sendMessageWithAttachment(user.getEmail(), subject, content);
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
        try {
            return this.treatmentRepository.findAllByGivenCriteria(criteria).get()
                    .stream()
                    .map(t -> this.modelMapper.map(t, TreatmentViewModel.class))
                    .sorted(Comparator.comparing(TreatmentViewModel::getDateAdded).reversed())
                    .collect(Collectors.toList());
        } catch (Exception ignored) {}
        return null;
    }
}
