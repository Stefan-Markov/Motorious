package projectdefence.service;

import projectdefence.models.entities.Measurement;
import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.viewModels.MeasurementViewModel;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {

    void addMeasurement(MeasurementAddServiceModel measurementAddServiceModel, String username, String name);

    List<MeasurementViewModel> findAllMeasurementsByUsername(String username);

    void deleteById(String id);

}
