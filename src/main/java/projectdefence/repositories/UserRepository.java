package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "select u from User u where  u.username = :username")
    Optional<User> findByUsernameOptional(@Param("username") String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    Optional<User> findUserByUsername(String username);

    List<User> findAllByTitle(String title);
}
