package projectdefence.event.userDeleteEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import projectdefence.models.entities.User;

@Component
public class DeleteEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DeleteEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void publishUserDeleteEvent(User user) {
        UserDeleteEvent userDeleteEvent = new UserDeleteEvent(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername());
        applicationEventPublisher.publishEvent(userDeleteEvent);
    }
}
