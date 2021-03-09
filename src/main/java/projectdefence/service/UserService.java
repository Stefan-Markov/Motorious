package projectdefence.service;

import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;

public interface UserService {
    boolean register(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);


    void changeRole(UserServiceChangeRoleModel userServiceModel, String role);
}
