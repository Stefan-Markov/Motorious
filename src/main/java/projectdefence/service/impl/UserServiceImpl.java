package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projectdefence.event.userDeleteEvent.DeleteEventPublisher;
import projectdefence.event.userRegisterEvent.RegisterEventPublisher;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserViewModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.repositories.RoleRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.repositories.UserSearchSpecification;
import projectdefence.service.CloudinaryService;
import projectdefence.service.RoleService;
import projectdefence.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static projectdefence.messages.RoleValue.*;


@Service
public class UserServiceImpl implements UserService {
    private static final String KT_TITLE = "kinesitherapist";
    private static final String CLIENT_TITLE = "client";
    private static final String INNER_PIC_PATH = "/img/user.jpeg";
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final RegisterEventPublisher registerEventPublisher;
    private final DeleteEventPublisher deleteEventPublisher;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, RoleService roleService,
                           RoleRepository roleRepository, CloudinaryService cloudinaryService,
                           RegisterEventPublisher registerEventPublisher,
                           DeleteEventPublisher deleteEventPublisher) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.cloudinaryService = cloudinaryService;
        this.registerEventPublisher = registerEventPublisher;
        this.deleteEventPublisher = deleteEventPublisher;
    }


    @Override
    public boolean register(UserServiceModel userServiceModel) {
        try {
            this.roleService.seedRolesInDb();

            if (this.userRepository.count() == 0) {
                userServiceModel.setAuthorities(this.roleService.findAllRoles());
            } else {
                userServiceModel.setAuthorities(new HashSet<>());

                if (userServiceModel.getTitle().equals(KT_TITLE)) {
                    // for demo use only 
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority(USER));
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority(KINESITHERAPIST));
                } else {
                    userServiceModel.setTitle(CLIENT_TITLE);
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority(USER));
                }
            }

            User user = this.modelMapper.map(userServiceModel, User.class);

            if (userServiceModel.getImage().isEmpty()) {
                user.setImageUrl(INNER_PIC_PATH);
            } else {
                MultipartFile image = userServiceModel.getImage();
                String imageUrl = cloudinaryService.uploadImage(image);
                user.setImageUrl(imageUrl);
            }

            user.setCreatedDate(LocalDateTime.now());
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            registerEventPublisher.publishUserRegisterEvent(userServiceModel);

            UserDetails principal = userRepository.findByUsername(user.getUsername());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    user.getPassword(),
                    principal.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<UserWrapInfoViewModel> findByGivenUsername(String username) {
        return this.userRepository.findAllByGivenUsername(username).stream()
                .map(user -> modelMapper
                        .map(user, UserWrapInfoViewModel.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public UserServiceModel findByUsernameAndPassword(String username, String password) {
//        return this.userRepository.findByUsernameAndPassword(username, password)
//                .map(user -> modelMapper
//                        .map(user, UserServiceModel.class))
//                .orElse(null);
//    }

    @Override
    public void changeRole(UserServiceChangeRoleModel userServiceModel, String role) {
        Optional<User> user = userRepository.findUserByUsername(userServiceModel.getUsername());
        if (user.isPresent()) {
            Role newRole = this.roleRepository.findByAuthority(role);
            if (newRole != null) {
                Set<Role> tryNewRole = new HashSet<>(user.get().getAuthorities());
                if (!tryNewRole.contains(newRole) && !newRole.getAuthority().equals(ROOT)) {
                    user.get().getAuthorities().add(newRole);
                    this.userRepository.save(user.get());
                }
            }
        }
    }

    @Override
    public void downgradeRole(UserServiceChangeRoleModel userServiceModel, String role) {
        Optional<User> user = userRepository.findUserByUsername(userServiceModel.getUsername());
        if (user.isPresent()) {
            Role removeRole = this.roleRepository.findByAuthority(role);
            if (removeRole != null) {
                Set<Role> tryRemoveRole = new HashSet<>(user.get().getAuthorities());
                if (tryRemoveRole.contains(removeRole) && !removeRole.getAuthority().equals(ROOT)) {
                    user.get().getAuthorities().remove(removeRole);
                    this.userRepository.save(user.get());
                }
            }
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        String rootAdminName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!rootAdminName.equals(username)) {
            Optional<User> user = userRepository.findUserByUsername(username);
            user.ifPresent(deleteEventPublisher::publishUserDeleteEvent);
            user.ifPresent(this.userRepository::delete);
        }
    }

    @Override
    @CachePut("users")
    public List<UserWrapInfoViewModel> findAllUsers() {
        return this.userRepository.findAllOrderByDate().stream().map(u ->
                        this.modelMapper.map(u, UserWrapInfoViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWrapInfoViewModel> getUsersPage(int page, int limit) {

        if (page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        //  Sort.by("firstName").and(Sort.by("lastName"))

        Page<User> usersPage = userRepository.findAll(pageableRequest);
        List<User> users = usersPage.getContent()
                .stream()
                .sorted(Comparator.comparing(User::getUsername)
                        .thenComparing(User::getFirstName))
                .collect(Collectors.toList());

        return users.stream()
                .map(e -> modelMapper.map(e, UserWrapInfoViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findByTitleKT() {
        return this.userRepository.findAllByTitle(KT_TITLE).size();
    }

    @Override
    public Integer findByTitleClient() {
        return this.userRepository.findAllByTitle(CLIENT_TITLE).size();
    }

    @Override
    public boolean findByUsername(String username) {
        return userRepository.findByUsernameOptional(username).isPresent();
    }

    @Override
    public String findImageByUsername(String username) {
        return this.userRepository.findImageUrl(username);
    }

    @Override
    public List<UserWrapInfoViewModel> findAllUsersByKinesiotherapist(String name) {
        return this.userRepository.findAllByKinesitherapistName(name)
                .stream().map(c -> this.modelMapper
                        .map(c, UserWrapInfoViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserWrapInfoViewModel findProfileByUserName(String username) {
        User user = this.userRepository.findOne(new UserSearchSpecification(username)).get();
        return this.modelMapper.map(user, UserWrapInfoViewModel.class);
    }

    @Override
    public boolean editProfile(UserServiceModel userServiceModel, String username) {
        User user = this.userRepository.findByUsername(username);
        try {
            if (!userServiceModel.getImage().isEmpty()) {
                MultipartFile image = userServiceModel.getImage();
                String imageUrl = cloudinaryService.uploadImage(image);
                user.setImageUrl(imageUrl);
            }
            user.setFirstName(userServiceModel.getFirstName());
            user.setLastName(userServiceModel.getLastName());
            user.setEmail(userServiceModel.getEmail());
            user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

            this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public UserViewModel findByUsernameUser(String username) {
        User user = this.userRepository.findByUsername(username);
        return this.modelMapper.map(user, UserViewModel.class);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getOrCreateUser(String filteredUsername, OAuth2User principal) {
        String username = filteredUsername.replaceAll("\\s+", "");
        User user = this.userRepository.findByUsername(username);
        if (user != null) {

            return user;
        }
        UserServiceModel userServiceModel = new UserServiceModel();

        userServiceModel.setAuthorities(new HashSet<>());
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority(USER));

        User newUser = this.modelMapper.map(userServiceModel, User.class);
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setUsername(username);
        newUser.setFirstName(username);
        newUser.setLastName(username);
        newUser.setEmail(username);

        newUser.setTitle(CLIENT_TITLE);

        if (newUser.getPassword() != null) {
            newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        }

        newUser.setImageUrl(INNER_PIC_PATH);

        this.userRepository.save(newUser);

        return newUser;
    }
}


