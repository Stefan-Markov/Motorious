package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Treatment;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, String> {

    @Query("select t from Treatment t where t.disease like %:criteria%")
    List<Treatment> findAllByGivenCriteria(String criteria);
}
