package projectdefence.service;

import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.viewModels.MeasurementByUserNameViewModel;

import java.util.List;

public interface MeasurementService {
    void addMeasurement(MeasurementAddServiceModel measurementAddServiceModel, String username, String name);

    List<MeasurementByUserNameViewModel> findAllMeasurementsByUsername(String username);

    void deleteById(String id);
}
