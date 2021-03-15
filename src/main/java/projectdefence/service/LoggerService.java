package projectdefence.service;

import org.springframework.context.ApplicationEvent;

import java.io.IOException;

public interface LoggerService {

     void log(ApplicationEvent applicationEvent) throws IOException;
}
