package projectdefence.event.userRegisterEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import projectdefence.models.serviceModels.UserServiceModel;

@Component
public class RegisterEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public RegisterEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void publishUserRegisterEvent(UserServiceModel userServiceModel) {
        UserRegisterEvent userRegisterEvent = new UserRegisterEvent(
                userServiceModel.getFirstName(),
                userServiceModel.getLastName(),
                userServiceModel.getUsername());
        applicationEventPublisher.publishEvent(userRegisterEvent);
    }
}
