package projectdefence.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.User;
import projectdefence.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class MotoriousUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MotoriousUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository
                .findUserByUsername(username);

        return userOpt.map(this::map).orElseThrow(
                () -> new UsernameNotFoundException("No user " + username));
    }

    private UserDetails map(User user) {

        List<GrantedAuthority> authorities = user.
                getAuthorities().
                stream().
                map(r -> new SimpleGrantedAuthority(r.getAuthority())).
                collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword() != null ? user.getPassword() : "",
                authorities);
    }
}
