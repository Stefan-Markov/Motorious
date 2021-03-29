package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import projectdefence.event.userDeleteEvent.DeleteEventPublisher;
import projectdefence.event.userRegisterEvent.RegisterEventPublisher;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.RoleServiceModel;
import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserViewModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.repositories.RoleRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserServiceTest {
    private UserService userService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;
    private RoleRepository roleRepository;
    private RegisterEventPublisher registerEventPublisher;
    private DeleteEventPublisher deleteEventPublisher;
    private CloudinaryService cloudinaryService;
    private UserServiceModel userServiceModel;
    private User user;

    @Before
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        roleService = Mockito.mock(RoleService.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        registerEventPublisher = Mockito.mock(RegisterEventPublisher.class);
        deleteEventPublisher = Mockito.mock(DeleteEventPublisher.class);
        cloudinaryService = Mockito.mock(CloudinaryService.class);

        userService = new UserServiceImpl(new ModelMapper(), userRepository,
                bCryptPasswordEncoder, roleService,
                roleRepository, cloudinaryService,
                registerEventPublisher,
                deleteEventPublisher);

        RoleServiceModel role = new RoleServiceModel();
        role.setAuthority("ROLE_USER");
        userServiceModel = new UserServiceModel();
        userServiceModel.setId("one")
                .setTitle("client")
                .setAuthorities(new HashSet<>())
                .setPassword("password")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setImage(null)
                .setUsername("username");
        userServiceModel.getAuthorities().add(role);

        Role roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");
        user = new User();
        user
                .setTitle("client")
                .setAuthorities(new HashSet<>())
                .setPassword("password")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setImageUrl("www.image.url")
                .setUsername("username");
        user.getAuthorities().add(roleUser);

        Mockito.when(userRepository.count())
                .thenReturn(1L);
    }

    @Test
    public void testRegisterUser() {
        Mockito.when(roleService.findByAuthority("ROLE_USER"))
                .thenReturn(new RoleServiceModel() {{
                    setAuthority("ROLE_USER");
                }});

        Mockito.when(userRepository.saveAndFlush(any(User.class)))
                .thenReturn(user);

        Assert.assertEquals(userRepository.count(), 1);
        Assert.assertEquals(user.getAuthorities().size(), 1);
    }

    @Test
    public void testRegisterUserShouldReturnFalse() {
        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        Mockito.when(roleService.findByAuthority("ROLE_USER"))
                .thenReturn(new RoleServiceModel() {{
                    setAuthority("ROLE_USER");
                }});

        boolean register = this.userService.register(userServiceModel);

        Assert.assertFalse(register);
    }


    @Test
    public void testFindByUsernameViewModel() {
        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        UserViewModel userViewModel = userService.findByUsernameUser("username");

        assertEquals(user.getFirstName(), userViewModel.getFirstName());
        assertEquals(user.getLastName(), userViewModel.getLastName());
    }

    @Test
    public void testFindByUsernameOptional() {
        Mockito.when(userRepository.findByUsernameAndPassword("username", "password"))
                .thenReturn(Optional.of(user));


        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");

    }

    @Test
    public void testFindByUsernameAndPassword() {
        Mockito.when(userRepository.findByUsernameAndPassword("username", "password"))
                .thenReturn(Optional.of(user));

        assertEquals(user.getUsername(), "username");
        assertEquals(user.getPassword(), "password");
    }

    @Test
    public void testFindByUsernameAndPasswordShouldFail() {
        Mockito.when(userRepository.findByUsernameAndPassword("username", "password"))
                .thenReturn(Optional.of(user));

        assertEquals(user.getUsername(), "username");
        assertNotEquals(user.getPassword(), "password123");
    }

    @Test
    public void testDowngradeRole() {

        Mockito.when(userRepository.findUserByUsername("username"))
                .thenReturn(Optional.of(user));

        UserServiceChangeRoleModel userServiceChangeRoleModel = new UserServiceChangeRoleModel();
        userServiceChangeRoleModel.setUsername("username");
        Role newRole = new Role();
        newRole.setAuthority("ROLE_ADMIN");

        Mockito.when(roleRepository.findByAuthority("ROLE_ADMIN"))
                .thenReturn(newRole);

        userService.downgradeRole(userServiceChangeRoleModel, "ROLE_ADMIN");

        Assert.assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void testFindUserByUsername() {

        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        User username = userService.findUserByUsername("username");

        Assert.assertEquals(username.getUsername(), user.getUsername());
    }


    @Test
    public void testDeleteByUsername() {

        Mockito.when(userRepository.findUserByUsername("username"))
                .thenReturn(Optional.of(user));

        userService.deleteUserByUsername("username");

        Mockito.when(userRepository.findUserByUsername("username"))
                .thenReturn(null);
        Assert.assertNull(userRepository.findUserByUsername("username"));
    }

    @Test
    public void testFindProfileByUserName() {

        Mockito.when(userRepository.findByUsername("username"))
                .thenReturn(user);

        UserWrapInfoViewModel userWrapInfoViewModel = userService.findProfileByUserName("username");

        Assert.assertEquals(userWrapInfoViewModel.getUsername(), user.getUsername());
    }

    @Test
    public void testChangeRole() {

        Mockito.when(userRepository.findUserByUsername("username"))
                .thenReturn(Optional.of(user));
        UserServiceChangeRoleModel userServiceChangeRoleModel = new UserServiceChangeRoleModel();
        userServiceChangeRoleModel.setUsername("username");
        userService.changeRole(userServiceChangeRoleModel, "ROLE_ADMIN");

        int size = user.getAuthorities().size();

        Assert.assertEquals(2, size);

    }

    @Test
    public void testFindAllUsersByKinesiotherapist() {
        Mockito.when(userRepository.findAllByKinesitherapistName("username"))
                .thenReturn(List.of(user));

        List<UserWrapInfoViewModel> username = userService.findAllUsersByKinesiotherapist("username");
        Assert.assertEquals(1, username.size());
    }


    @Test
    public void testUserProperties() {
        Role role = new Role();
        role.setAuthority("ROLE_KINESITHERAPIST");

        User user = new User();
        user.setUsername("username")
                .setLastName("testov")
                .setFirstName("test")
                .setPassword("test123")
                .setEmail("mail@mail.bg")
                .setTitle("client")
                .setAuthorities(Set.of(role));

        Mockito.when(userRepository.findByUsername("username")).
                thenReturn(user);

        User username = userRepository.findByUsername("username");

        Assertions.assertEquals(user.getUsername(), username.getUsername());

        Assertions.assertEquals(1, (long) user.getAuthorities().size());
    }
}
