package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, String> {
}
