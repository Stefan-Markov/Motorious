package projectdefence.service;

import projectdefence.models.viewModels.LogServiceModel;

import java.util.List;

public interface LogService {
    void createLog(String action, String measurementId);

    List<LogServiceModel> findAllLogs();

    void deleteAll();
}
