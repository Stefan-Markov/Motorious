package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByUsernameAndPassword(String username,String password);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    boolean existsByEmail(String email);
}
