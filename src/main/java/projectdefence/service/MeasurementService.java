package projectdefence.service;

import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.viewModels.MeasurementViewModel;

import java.util.List;

public interface MeasurementService {

    void addMeasurement(MeasurementAddServiceModel measurementAddServiceModel, String username, String name);

    List<MeasurementViewModel> findAllMeasurementsByUsername(String username);

    void deleteById(String id);

}
