package projectdefence.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import projectdefence.service.LoggerService;

import java.io.IOException;

@Component
public class RegisterEventListener {
    private final LoggerService loggerService;

    public RegisterEventListener(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @EventListener(UserRegisterEvent.class)
    public void onUserRegisterEvent(UserRegisterEvent userRegisterEvent) throws IOException {
        loggerService.log(userRegisterEvent);
    }
}
