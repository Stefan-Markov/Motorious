package projectdefence.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "Leonkov")
    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/home"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("user", "Leonkov"));
    }

    @WithAnonymousUser
    @Test
    public void testHomeAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/user/login"));
    }

    @WithAnonymousUser
    @Test
    void testInfoPageGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(view().name("index"));
    }
}
