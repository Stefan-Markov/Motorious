package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.UserWrapInfoViewModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where  u.username = :username")
    Optional<User> findByUsernameOptional(@Param("username") String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    Optional<User> findUserByUsername(String username);

    List<User> findAllByTitle(String title);

    @Query("SELECT u.imageUrl from  User u where u.username = :username")
    String findImageUrl(@Param("username") String username);

    @Query("select distinct u from User u, Treatment t, Measurement  m" +
            " where t.createdBy = :name or m.createdBy = :name order by u.createdDate desc")
    List<User> findAllByKinesitherapistName(@Param("name") String name);


    @Query("select u from  User  u order by u.createdDate desc ")
    List<User> findAllOrderByDate();
}
