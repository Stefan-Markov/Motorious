package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Blog;
import projectdefence.models.viewModels.BlogViewModel;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "SELECT * from blog as b order by b.date desc limit 120", nativeQuery = true)
    List<Blog> findFirst120_OrderByDate();
}
