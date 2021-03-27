package projectdefence.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void testUserRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/registration"))
                .andExpect(view().name("registration"));
    }


    @Test
    @WithAnonymousUser
    public void testUserRegistrationPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .param("firstName", "test")
                .param("lastName", "testov")
                .param("username", "Testov-username")
                .param("email", "test@mail.bg")
                .param("password", "test")
                .param("confirmPassword", "test")
                .param("title", "client").
                        with(csrf())).
                andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }


    @Test
    @WithAnonymousUser
    public void testUserRegistrationPostShouldReturnFalseIsExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .param("firstName", "test")
                .param("lastName", "testov")
                .param("username", "Testov-username")
                .param("email", "test@mail.bg")
                .param("password", "test")
                .param("confirmPassword", "test")
                .param("title", "client")
                .flashAttr("isExists", true).
                        with(csrf())).
                andExpect(MockMvcResultMatchers.redirectedUrl("registration"));
    }


    @Test
    @WithAnonymousUser
    public void testUserRegistrationPostShouldReturnFalseNotSamePasswords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .param("firstName", "test")
                .param("lastName", "testov")
                .param("username", "Testov-username")
                .param("email", "test@mail.bg")
                .param("password", "test")
                .param("confirmPassword", "test")
                .param("title", "client")
                .flashAttr("notSame", true).
                        with(csrf())).
                andExpect(MockMvcResultMatchers.redirectedUrl("registration"));
    }

    @Test
    @WithMockUser(username = "Leonkov", roles = "ROOT")
    public void deleteUserShouldWorkProper() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/delete-user")
                .param("username", "username-to-delete")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("delete-user"));
    }


    @Test
    @WithMockUser(username = "Leonkov", roles = "ROOT")
    public void deleteUserGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete-user"))
                .andExpect(view().name("delete-user"));
    }

    @Test
    @WithMockUser(username = "Leonkov", roles = "USER")
    public void deleteUserAccessDeniedPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/delete-user")
                .param("username", "username-to-delete")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "Leonkov", roles = "USER")
    public void deleteUserAccessDeniedGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete-user")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @Test
    @WithAnonymousUser
    public void failedLoginFallBack() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login-error")
                .param("username", "username-test")
                .param("password", "password-test")
                .flashAttr("notFound", true))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "Leonkov", roles = "ROOT")
    public void allUsersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all-users"))
                .andExpect(view().name("view_all_users"));
    }

    @Test
    @WithMockUser(username = "Leonkov", roles = "USER")
    public void allUsersGetAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all-users"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }
}
