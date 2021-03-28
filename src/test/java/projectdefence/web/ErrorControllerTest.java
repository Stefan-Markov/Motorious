package projectdefence.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/error"))
                .andExpect(view().name("error/error"));
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testErrorAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/access-denied"))
                .andExpect(view().name("/error/error_high_voltage"));
    }
}

