package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Blog;
import projectdefence.models.viewModels.BlogViewModel;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b from Blog b order by b.date desc")
    List<Blog> findAllOrderByDate();
}
