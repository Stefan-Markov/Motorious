package projectdefence.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projectdefence.models.binding.UserChangeRoleBindingModel;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.repositories.UserRepository;
import projectdefence.service.UserService;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;


    @BeforeEach
    public void setup() {
        this.init();
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testChangeRoleAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/role/role-change"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "Leonkov", roles = "ADMIN")
    @Test
    public void testChangeRolePostNotFoundUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/role/role-change")
                .param("role", "role-test")
                .flashAttr("userFound", true)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("role-change"));
    }

    @WithMockUser(username = "Leonkov", roles = "ADMIN")
    @Test
    public void testChangeRolePostAddFlashAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/role/role-change")
                .param("role", "role-test")
                .flashAttr("userChangeRoleBindingModel", new UserChangeRoleBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("role-change"));
    }

    @WithMockUser(username = "Leonkov", roles = "ROOT")
    @Test
    public void testDowngradeRoleUserNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/role/role-downgrade")
                .param("role", "ADMIN")
                .param("username", "username")
                .flashAttr("userFound", true)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("role-downgrade"));
    }


    @WithMockUser(username = "Leonkov", roles = "ROOT")
    @Test
    public void testDowngradeRoleAddFlashAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/role/role-downgrade")
                .param("role", "ADMIN")
                .param("username", "username")
                .flashAttr("userChangeRoleBindingModel", new UserChangeRoleBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("role-downgrade"));
    }


    @WithMockUser(username = "Leonkov", roles = "ROOT")
    @Test
    public void testDowngradeRoleGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/role/role-downgrade"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("role_downgrade"));
    }

    @WithMockUser(username = "Leonkov", roles = "ADMIN")
    @Test
    public void testChangeRoleGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/role/role-change"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("role_change"));
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
        this.userRepository.save(user);
    }
}
