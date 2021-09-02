package projectdefence.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import projectdefence.models.entities.User;
import projectdefence.models.serviceModels.UserServiceChangeRoleModel;
import projectdefence.models.serviceModels.UserServiceModel;
import projectdefence.models.viewModels.UserViewModel;
import projectdefence.models.viewModels.UserWrapInfoViewModel;

import java.util.List;

public interface UserService {
    boolean register(UserServiceModel userServiceModel);

//    UserServiceModel findByUsernameAndPassword(String username, String password);

    List<UserWrapInfoViewModel> findByGivenUsername(String username);

    void changeRole(UserServiceChangeRoleModel userServiceModel, String role);

    void downgradeRole(UserServiceChangeRoleModel userServiceChangeRoleModel, String role);

    void deleteUserByUsername(String username);

    List<UserWrapInfoViewModel> findAllUsers();

    List<UserWrapInfoViewModel> getUsersPage(int page, int limit);

    Integer findByTitleKT();

    Integer findByTitleClient();

    boolean findByUsername(String username);

    String findImageByUsername(String image);

    List<UserWrapInfoViewModel> findAllUsersByKinesiotherapist(String name);

    UserWrapInfoViewModel findProfileByUserName(String username);

    boolean editProfile(UserServiceModel userServiceModel, String username);

    UserViewModel findByUsernameUser(String username);

    User findUserByUsername(String username);

    User getOrCreateUser(String username, OAuth2User principal);
}
