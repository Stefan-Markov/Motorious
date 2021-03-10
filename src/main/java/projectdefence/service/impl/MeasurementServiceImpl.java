package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Measurement;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.repositories.MeasurementRepository;
import projectdefence.repositories.UserRepository;
import projectdefence.service.MeasurementService;

import java.time.LocalDate;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final ModelMapper modelMapper;
    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public MeasurementServiceImpl(ModelMapper modelMapper, MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addMeasurement(MeasurementAddServiceModel measurementAddServiceModel, String username) {
        User user = this.userRepository.findByUsername(username);
        Measurement measurement = this.modelMapper.map(measurementAddServiceModel, Measurement.class);
        measurement.setUser(user);
        measurement.setDate(LocalDate.now());

        this.measurementRepository.save(measurement);
    }
}
