package projectdefence.event.userDeleteEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import projectdefence.service.LoggerService;

import java.io.IOException;

@Component
public class DeleteEventListener {
    private final LoggerService loggerService;

    public DeleteEventListener(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @EventListener(UserDeleteEvent.class)
    public void userDeleteEvent(UserDeleteEvent userDeleteEvent) throws IOException {
        loggerService.logDelete(userDeleteEvent);
    }
}
