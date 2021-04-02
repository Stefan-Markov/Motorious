package projectdefence.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @WithAnonymousUser
    @Test
    public void testInfoExercise() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/info/exercise"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("exercise_info"));
    }

    @WithAnonymousUser
    @Test
    public void testInfoMassage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/info/massage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("massage_info"));
    }


    @WithAnonymousUser
    @Test
    public void testInfoPhysiotherapy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/info/physiotherapy"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("physiotherapy_info"));
    }

    @WithAnonymousUser
    @Test
    public void testInfoBlog() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/info/blog"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("blog"));
    }
}
