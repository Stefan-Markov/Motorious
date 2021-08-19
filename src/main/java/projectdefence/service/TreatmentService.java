package projectdefence.service;

import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TreatmentService {

    void addTreatment(TreatmentAddServiceModel treatmentAddServiceModel, String username, String nameKt);

    List<TreatmentViewModel> findAllTreatmentsByUsername(String username);

    void deleteById(String id);

    List<TreatmentViewModel> findAllByCriteria(String criteria);
}
