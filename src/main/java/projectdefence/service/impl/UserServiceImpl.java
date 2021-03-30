package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import projectdefence.service.*;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final String KT_TITLE = "kinesitherapist";
    private static final String CLIENT_TITLE = "client";
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final RegisterEventPublisher registerEventPublisher;
    private final DeleteEventPublisher deleteEventPublisher;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           PasswordEncoder bCryptPasswordEncoder, RoleService roleService,
                           RoleRepository roleRepository, CloudinaryService cloudinaryService,
                           RegisterEventPublisher registerEventPublisher,
                           DeleteEventPublisher deleteEventPublisher) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_KINESITHERAPIST"));
                } else {
                    userServiceModel.setTitle(CLIENT_TITLE);
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                }
            }

            User user = this.modelMapper.map(userServiceModel, User.class);

            if (userServiceModel.getImage().isEmpty()) {
                user.setImageUrl("/img/user.jpeg");
            } else {
                MultipartFile image = userServiceModel.getImage();
                String imageUrl = cloudinaryService.uploadImage(image);
                user.setImageUrl(imageUrl);
            }

            user.setCreatedDate(LocalDateTime.now());
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

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
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password)
                .map(user -> modelMapper
                        .map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void changeRole(UserServiceChangeRoleModel userServiceModel, String role) {
        Optional<User> user = userRepository.findUserByUsername(userServiceModel.getUsername());
        if (user.isPresent()) {
            Role newRole = this.roleRepository.findByAuthority(role);
            Set<Role> tryNewRole = new HashSet<>(user.get().getAuthorities());
            if (!tryNewRole.contains(newRole) && !newRole.getAuthority().equals("ROLE_ROOT")) {
                user.get().getAuthorities().add(newRole);
                this.userRepository.save(user.get());
            }
        }
    }

    @Override
    public void downgradeRole(UserServiceChangeRoleModel userServiceModel, String role) {
        Optional<User> user = userRepository.findUserByUsername(userServiceModel.getUsername());
        if (user.isPresent()) {
            Role removeRole = this.roleRepository.findByAuthority(role);
            Set<Role> tryRemoveRole = new HashSet<>(user.get().getAuthorities());
            if (tryRemoveRole.contains(removeRole) && !removeRole.getAuthority().equals("ROLE_ROOT")) {
                user.get().getAuthorities().remove(removeRole);
                this.userRepository.save(user.get());
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
        User user = this.userRepository.findByUsername(username);
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
            user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

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
}


