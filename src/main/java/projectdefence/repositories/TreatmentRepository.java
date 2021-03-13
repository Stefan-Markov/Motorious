package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectdefence.models.entities.Treatment;
import projectdefence.models.entities.User;

import java.util.List;


@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, String> {




}
