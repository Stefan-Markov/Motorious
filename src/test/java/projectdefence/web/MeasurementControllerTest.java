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
import projectdefence.models.binding.MeasurementAddBindingModel;
import projectdefence.models.viewModels.LogServiceModel;
import projectdefence.service.LogService;
import projectdefence.service.MeasurementService;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementService measurementService;
    @MockBean
    private LogService logService;

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testMeasurementAddGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/measurement/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("measurement_add"));
    }


    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testMeasurementAddGetAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/measurement/add"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "Leonkov", roles = "ADMIN")
    @Test
    public void testMeasurementLogsGet() throws Exception {

        List<LogServiceModel> allLogs = this.logService.findAllLogs();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/measurement/logs")
                .param("logs", "allLogs"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("measurements_log"));
    }


    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testMeasurementAddPostFlashAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/measurement/add")
                .param("nameKt", "username-nameKt")
                .flashAttr("measurementAddBindingModel", new MeasurementAddBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("add"));
    }


    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testMeasurementAddPostUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/measurement/add")
                .param("nameKt", "username-nameKt")
                .flashAttr("userFound", true)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("add"));
    }

    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testMeasurementCheckGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/measurement/check")
                .param("username", "Leonkov"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("measurement-check"));
    }
}
