package projectdefence.service;

import org.junit.Before;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import projectdefence.repositories.BlogRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.BlogServiceImpl;


public class BlogServiceTest {

    private BlogService blogService;
    private BlogRepository blogRepository;
    private UserRepository userRepository;


    @Before
    public void init() {
        blogRepository = Mockito.mock(BlogRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        blogService = new BlogServiceImpl(blogRepository, new ModelMapper(), userRepository);
    }


}
