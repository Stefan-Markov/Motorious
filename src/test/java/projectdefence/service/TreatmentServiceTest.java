package projectdefence.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;
import projectdefence.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTest {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    private UserService userServiceTest;

    @Test
    void testFindAllTreatments(){
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

        Treatment treatment = new Treatment();
        treatment.setKtFullName("Testov")
                .setDateAdded(LocalDate.now())
                .setContent("bla-bla")
                .setDisease("no idea")
                .setVisits(1)
                .setGoal("goal")
                .setDuration(1.0);

        user.setTreatments(List.of(treatment));

        Assertions.assertEquals(1,user.getTreatments().size());

    }
}
