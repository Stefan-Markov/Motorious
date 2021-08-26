package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import projectdefence.models.entities.Blog;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.BlogViewModel;
import projectdefence.repositories.BlogRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.BlogServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class BlogServiceTest {

    private EmailService emailService;
    private BlogService blogService;
    private BlogRepository blogRepository;
    private UserRepository userRepository;
    private User user;
    private Blog blog;

    @Before
    public void init() {
        blogRepository = Mockito.mock(BlogRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        blogService = new BlogServiceImpl(emailService, blogRepository, new ModelMapper(), userRepository);

        Role roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");
        user = new User();
        user
                .setTitle("client")
                .setAuthorities(Set.of(roleUser))
                .setPassword("password")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setImageUrl("www.image.url")
                .setUsername("username");


        Mockito.when(userRepository.count())
                .thenReturn(1L);

        blog = new Blog();
        blog.setAuthor(user.getFirstName() + " " + user.getLastName())
                .setUser(List.of(user))
                .setDate(LocalDate.now())
                .setContent("content")
                .setTitle("title");

        Mockito.when(blogRepository.count())
                .thenReturn(1L);
    }

    @Test
    public void testAllBlogs() {

        Mockito.when(blogRepository.findAll())
                .thenReturn(List.of(blog));

        List<BlogViewModel> all = this.blogService.findAll();

        Assert.assertEquals(1, all.size());
    }
}
