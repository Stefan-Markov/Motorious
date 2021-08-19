package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Treatment;

import java.util.List;
import java.util.concurrent.Future;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, String> {

    @Async
    @Query("select t from Treatment t where t.disease like %:criteria%")
    Future<List<Treatment>> findAllByGivenCriteria(String criteria);
}
