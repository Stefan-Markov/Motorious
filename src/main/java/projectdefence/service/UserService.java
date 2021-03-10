package projectdefence.service;

import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;

import java.util.List;

public interface UserService {
    boolean register(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);


    void changeRole(UserServiceChangeRoleModel userServiceModel, String role);

    void downgradeRole(UserServiceChangeRoleModel userServiceChangeRoleModel, String role);


    void deleteUserByUsername(String username);

    List<UserWrapInfoViewModel> findAllUsers();

    Integer findByTitleKT();

    Integer findByTitleClient();

    boolean findByUsername(String username);
}
