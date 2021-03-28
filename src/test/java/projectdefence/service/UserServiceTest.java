package projectdefence.service;

import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;


import projectdefence.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    private UserService userServiceTest;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        this.init();
    }

    @Test
    void testFindByUsernameReturnFalse() {
        Assertions.assertFalse(
                userServiceTest.findByUsername("user_does_not_exits"));
    }


    @Test
    void testFindByUsernameAndPassword() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");

        User user = new User();
        user.setUsername("username")
                .setLastName("testov")
                .setFirstName("test")
                .setPassword("test123")
                .setEmail("mail@mail.bg")
                .setCreatedDate(LocalDateTime.now())
                .setAuthorities(Set.of(role));
        Mockito.when(mockUserRepository.findByUsernameAndPassword("username", "test123")).
                thenReturn(Optional.of(user));
        this.mockUserRepository.save(user);
        Optional<User> byUsernameAndPassword = this.mockUserRepository.findByUsernameAndPassword("username", "test123");

        Assertions.assertNotNull(byUsernameAndPassword);
    }

    @Test
    void testUserProperties() {
        Role role = new Role();
        role.setAuthority("ROLE_KINESITHERAPIST");

        User user = new User();
        user.setUsername("username")
                .setLastName("testov")
                .setFirstName("test")
                .setPassword("test123")
                .setEmail("mail@mail.bg")
                .setTitle("client")
                .setAuthorities(Set.of(role));

        Mockito.when(mockUserRepository.findByUsername("username")).
                thenReturn(user);

        User username = mockUserRepository.findByUsername("username");

        Assertions.assertEquals(user.getUsername(), username.getUsername());

        Assertions.assertEquals(1, (long) user.getAuthorities().size());
    }

    private void init() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");

        User user = new User();
        user.setUsername("username")
                .setLastName("testov")
                .setFirstName("test")
                .setPassword("test123")
                .setEmail("mail@mail.bg")
                .setCreatedDate(LocalDateTime.now())
                .setAuthorities(Set.of(role));
        this.mockUserRepository.save(user);
    }
}
