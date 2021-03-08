package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.repositories.UserRepository;
import projectdefence.service.RoleService;
import projectdefence.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final MotoriousUserDetailsService motoriousUserDetailsService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           PasswordEncoder bCryptPasswordEncoder, RoleService roleService, MotoriousUserDetailsService motoriousUserDetailsService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;


        this.roleService = roleService;
        this.motoriousUserDetailsService = motoriousUserDetailsService;
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
}
