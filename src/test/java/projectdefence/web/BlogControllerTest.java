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
import projectdefence.models.binding.AddBlogBindingModel;
import projectdefence.service.BlogService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @WithMockUser(username = "Testov", roles = "KINESITHERAPIST")
    @Test
    public void testViewAllClientGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/blog/add-blog")
                .flashAttr("addBlogBindingModel", new AddBlogBindingModel()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("add-blog"));
    }

    @WithMockUser(username = "Leonkov", roles = "KINESITHERAPIST")
    @Test
    public void testBlogAddPostFlashAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/blog/add-blog")
                .flashAttr("addBlogBindingModel", new AddBlogBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("add-blog"));
    }

    @WithMockUser(username = "Leonkov", roles = "USER")
    @Test
    public void testBlogAddPostAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/blog/add-blog")
                .flashAttr("addBlogBindingModel", new AddBlogBindingModel())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/access-denied"));
    }


    @WithMockUser(username = "Leonkov", roles = "ADMIN")
    @Test
    public void testBlogDeleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/blog/delete/{id}", "5")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}
