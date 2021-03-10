package projectdefence.service;

import projectdefence.models.serviceModels.MeasurementAddServiceModel;

public interface MeasurementService {
    void addMeasurement(MeasurementAddServiceModel measurementAddServiceModel , String username);
}
