package projectdefence.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projectdefence.service.MeasurementService;
import projectdefence.service.TreatmentService;
import projectdefence.service.UserService;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TreatmentService treatmentService;

    @MockBean
    private MeasurementService measurementService;


    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testViewAllClientGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/client/all")
                .param("username", "Leonkov")
                .flashAttr("noTreatment", true))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("view_all_clients"));
    }

    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testViewAllClientGetAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/client/all")
                .param("username", "Leonkov"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testViewClientProfileGet() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/client/info/{username}", "Testov")
                .param("username", "Leonkov"))
                .andExpect(view().name("info_by_username"));
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testDeleteMeasurementOrTreatment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/client/delete/{id}", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"));
    }
}
