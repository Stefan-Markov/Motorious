package projectdefence.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.Measurement;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.MeasurementViewModel;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.repositories.MeasurementRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.MeasurementServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class MeasurementServiceTest {

    private ModelMapper modelMapper;
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;
    private Measurement measurement;
    private MeasurementService measurementService;
    private User user;

    @Before
    public void init() {
        measurementRepository = Mockito.mock(MeasurementRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        measurementService = new MeasurementServiceImpl(new ModelMapper(), measurementRepository, userRepository);

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


        measurement = new Measurement();
        measurement.setUser(user)
                .setDate(LocalDate.now())
                .setKtFullName("full-name")
                .setContent("content")
                .setCreatedBy("created-by")
                .setAge(21)
                .setStage("stage")
                .setDisease("disease");


        user.setMeasurements(List.of(measurement));
        Mockito.when(measurementRepository.count())
                .thenReturn(1L);
    }

    @Test
    public void testFindAllMeasurementByUsername() {
        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        List<MeasurementViewModel> username =
                measurementService.findAllMeasurementsByUsername("username");
        int size = user.getMeasurements().size();

        Assert.assertEquals(size, username.size());
        Assert.assertEquals(1, username.size());

        user.setMeasurements(List.of(measurement, measurement));

        Assert.assertEquals(2, user.getMeasurements().size());
    }

    @Test
    public void testAddMeasurement() {

        Mockito.when(this.userRepository.findByUsername("username"))
                .thenReturn(user);

        measurement.setUser(user);

        Mockito.when(this.userRepository.findByUsername("nameKt"))
                .thenReturn(user);

        measurement.setKtFullName(user.getFirstName() + " " + user.getLastName())
                .setCreatedBy(user.getUsername());
        MeasurementAddServiceModel measurementAddServiceModel = new MeasurementAddServiceModel();
        measurementService.addMeasurement(measurementAddServiceModel, "username", "nameKt");

        Assert.assertEquals(1, user.getMeasurements().size());
    }
}
