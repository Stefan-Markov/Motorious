package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.LogUnit;
import projectdefence.models.entities.User;
import projectdefence.models.viewModels.LogServiceModel;
import projectdefence.repositories.LogRepository;
import projectdefence.service.LogService;
import projectdefence.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, UserService userService, ModelMapper modelMapper) {
        this.logRepository = logRepository;

        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void createLog(String action, String measurementId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        User userEntity = userService.findUserByUsername(username);

        LogUnit logEntity = new LogUnit()
                .setUser(userEntity)
                .setAction(action)
                .setDateTime(LocalDateTime.now());
        logRepository.save(logEntity);
    }

    @Override
    @CachePut("logs")
    public List<LogServiceModel> findAllLogs() {

        deleteAll();

        return logRepository
                .findAllOrderByDateTime()
                .stream()
                .map(log -> {
                    LogServiceModel logServiceModel = modelMapper
                            .map(log, LogServiceModel.class);
                    logServiceModel
                            .setUser(log.getUser().getUsername() + " // " + log.getUser().getFirstName()
                                    + " " + log.getUser().getLastName());
                    return logServiceModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        if (this.logRepository.count() > 1000) {
            this.logRepository.deleteAll();
        }
    }
}
