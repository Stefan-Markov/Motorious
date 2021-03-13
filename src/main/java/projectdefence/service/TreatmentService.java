package projectdefence.service;

import projectdefence.models.binding.TreatmentAddBindingModel;
import projectdefence.models.viewModels.TreatmentViewModel;

import java.util.List;

public interface TreatmentService {
    void addTreatment(TreatmentAddBindingModel treatmentAddBindingModel, String username, String nameKt);

    List<TreatmentViewModel> findAllTreatmentsByUsername(String username);

}
