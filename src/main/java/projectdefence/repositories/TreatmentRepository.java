package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Treatment;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, String> {

}
