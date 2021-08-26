package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.repositories.TreatmentRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.TreatmentServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TreatmentServiceTest {

    private ModelMapper modelMapper;
    private TreatmentRepository treatmentRepository;
    private UserRepository userRepository;
    private TreatmentService treatmentService;
    private Treatment treatment;
    private User user;

    @Before
    public void init() {
        treatmentRepository = Mockito.mock(TreatmentRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        EmailService emailService = null;
        treatmentService = new TreatmentServiceImpl(new ModelMapper(), treatmentRepository, userRepository, null);

        Role roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");

        user = new User();
        user.setTitle("client")
                .setAuthorities(Set.of(roleUser))
                .setPassword("password")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setImageUrl("www.image.url")
                .setUsername("username");


        treatment = new Treatment();
        treatment.setUser(user)
                .setDateAdded(LocalDate.now())
                .setKtFullName("full-name")
                .setContent("content")
                .setDuration(2.0)
                .setVisits(1)
                .setGoal("goal")
                .setDisease("disease");


        user.setTreatments(List.of(treatment));
        Mockito.when(treatmentRepository.count())
                .thenReturn(1L);
    }

    @Test
    public void testFindAllTreatmentByUsername() {
        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        List<TreatmentViewModel> username =
                treatmentService.findAllTreatmentsByUsername("username");
        int size = user.getTreatments().size();

        Assert.assertEquals(size, username.size());
        Assert.assertEquals(1, username.size());

        user.setTreatments(List.of(treatment, treatment));

        Assert.assertEquals(2, user.getTreatments().size());
    }

    @Test
    public void testAddTreatment() {

        Mockito.when(this.userRepository.findByUsername("username"))
                .thenReturn(user);

        treatment.setUser(user);

        Mockito.when(this.userRepository.findByUsername("nameKt"))
                .thenReturn(user);

        treatment.setKtFullName(user.getFirstName() + " " + user.getLastName())
                .setCreatedBy(user.getUsername());
        TreatmentAddServiceModel treatmentAddServiceModel = new TreatmentAddServiceModel();
        treatmentService.addTreatment(treatmentAddServiceModel, "username", "nameKt");

        Assert.assertEquals(1,user.getTreatments().size());
    }
}