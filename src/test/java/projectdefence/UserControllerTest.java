package projectdefence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("**/user/registration"))
                .andExpect(view().name("registration"))
                .andExpect((ResultMatcher) status(HttpStatus.OK))
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/user/login"));
    }

    @Test
    public void testUserLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("**/user/login"))
                .andExpect(view().name("login"))
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("/home"));
    }
}
