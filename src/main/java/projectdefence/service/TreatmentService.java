package projectdefence.service;

import projectdefence.models.binding.TreatmentAddBindingModel;

public interface TreatmentService {
    void addTreatment(TreatmentAddBindingModel treatmentAddBindingModel, String username, String nameKt);
}
