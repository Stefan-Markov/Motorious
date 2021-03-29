package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.LogUnit;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.LogServiceModel;
import projectdefence.repositories.LogRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.LogServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class LogServiceTest {


    private LogRepository logRepository;
    private UserService userService;
    private ModelMapper modelMapper;
    private LogService logService;
    private UserRepository userRepository;
    private LogUnit log;
    private User user;

    @Before
    public void init() {
        logRepository = Mockito.mock(LogRepository.class);
        userService = Mockito.mock(UserService.class);
        userRepository = Mockito.mock(UserRepository.class);
        logService = new LogServiceImpl(logRepository, userService, new ModelMapper());

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


        Mockito.when(logRepository.count())
                .thenReturn(1L);

        log = new LogUnit();
        log.setAction("action")
                .setDateTime(LocalDateTime.now())
                .setUser(user);
    }

    @Test
    public void testFindAll() {
        Mockito.when(logRepository.findAllOrderByDateTime())
                .thenReturn(List.of(log));

        List<LogServiceModel> allLogs = logService.findAllLogs();

        Assert.assertEquals(1, allLogs.size());
    }

    @Test
    public void testDeleteAll() {

        LogUnit logUnit = new LogUnit();
        logRepository.save(logUnit);

        logService.deleteAll();

        Assert.assertEquals(1, logRepository.count());
    }
}
