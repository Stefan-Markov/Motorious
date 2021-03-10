package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Role;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;
import projectdefence.repositories.RoleRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.RoleService;
import projectdefence.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           PasswordEncoder bCryptPasswordEncoder, RoleService roleService, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }


    @Override
    public boolean register(UserServiceModel userServiceModel) {
        try {
            this.roleService.seedRolesInDb();

            if (this.userRepository.count() == 0) {
                userServiceModel.setAuthorities(this.roleService.findAllRoles());
            } else {
                userServiceModel.setAuthorities(new HashSet<>());
                if (userServiceModel.getTitle().equals("client")) {
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                } else {
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_KINESITHERAPIST"));
                    userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                }
            }

            User user = this.modelMapper.map(userServiceModel, User.class);

            user.setCreatedDate(LocalDateTime.now());
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            user.setImageUrl("/img/user.jpeg");
            userRepository.save(user);

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
            if (!tryNewRole.contains(newRole)) {
                user.get().getAuthorities().add(newRole);
                this.userRepository.save(user.get());
            }
        }
    }

    @Override
    public void downgradeRole(UserServiceChangeRoleModel userServiceModel, String role) {
        Optional<User> user = userRepository.findUserByUsername(userServiceModel.getUsername());
        if (user.isPresent()) {
            Role newRole = this.roleRepository.findByAuthority(role);
            Set<Role> tryNewRole = new HashSet<>(user.get().getAuthorities());
            if (tryNewRole.contains(newRole)) {
                user.get().getAuthorities().remove(newRole);
                this.userRepository.save(user.get());
            }
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        user.ifPresent(this.userRepository::delete);
    }

    @Override
    public List<UserWrapInfoViewModel> findAllUsers() {
        return this.userRepository.findAll().stream().map(u ->
                this.modelMapper.map(u, UserWrapInfoViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findByTitleKT() {
        String title = "Kinesitherapist";
        return this.userRepository.findAllByTitle(title).size();
    }

    @Override
    public Integer findByTitleClient() {
        String title = "client";
        return this.userRepository.findAllByTitle(title).size();
    }
}


