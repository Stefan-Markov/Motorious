package projectdefence.web;

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
import projectdefence.models.binding.TreatmentAddBindingModel;
import projectdefence.service.TreatmentService;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TreatmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TreatmentService treatmentService;


    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testTreatmentAddWorkProper() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/treatment/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("userFound", false))
                .andExpect(view().name("treatment_add"));

    }

    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testTreatmentAddAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/treatment/add"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testTreatmentAddPostNotFoundUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/treatment/add")
                .param("username", "username-test")
                .param("disease", "test-disease")
                .param("goal", "test-goal")
                .param("duration", "1")
                .param("visits", "1")
                .param("content", "test-content")
                .param("createdBy", "test")
                .param("KtFullName", "test")
                .param("nameKt", "name-test")
                .flashAttr("userFound", true)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("add"));
    }


    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testTreatmentAddPostBindingErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/treatment/add")
                .param("username", "username-test")
                .param("disease", "test-disease")
                .param("goal", "test-goal")
                .param("duration", "1")
                .param("content", "test-content")
                .param("createdBy", "test")
                .param("KtFullName", "test")
                .param("nameKt", "test-name")
                .flashAttr("treatmentAddBindingModel", new TreatmentAddBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("add"));
    }

    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testTreatmentCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/treatment/check")
                .param("username", "Leonkov"))
                .andExpect(model().attribute("no", true))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("treatment_check"));
    }
}
