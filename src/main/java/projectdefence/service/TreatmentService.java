package projectdefence.service;

import projectdefence.models.serviceModels.TreatmentAddServiceModel;
import projectdefence.models.viewModels.TreatmentViewModel;

import java.util.List;

public interface TreatmentService {

    void addTreatment(TreatmentAddServiceModel treatmentAddServiceModel, String username, String nameKt);

    List<TreatmentViewModel> findAllTreatmentsByUsername(String username);

    void deleteById(String id);
}
