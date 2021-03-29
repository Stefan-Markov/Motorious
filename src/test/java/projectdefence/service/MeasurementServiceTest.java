package projectdefence.service;


import org.junit.Before;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.Treatment;
import projectdefence.repositories.MeasurementRepository;
import projectdefence.repositories.UserRepository;

public class MeasurementServiceTest {

    private ModelMapper modelMapper;
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;
    private Treatment treatment;
    private TreatmentService treatmentService;

    @Before
    public void init() {
        measurementRepository = Mockito.mock(MeasurementRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

    }
}
