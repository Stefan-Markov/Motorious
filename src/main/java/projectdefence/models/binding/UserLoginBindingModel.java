package projectdefence.models.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {

    private String username;
    private String password;

    public UserLoginBindingModel() {
    }

    @NotBlank(message = "Should be not empty")
    @Size(min = 3, max = 50, message = "Username must be minimum three characters")
    public String getUsername() {
        return username;
    }

    public UserLoginBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = "Should be not empty")
    @Size(min = 6, max = 50, message = "Password must be minimum six characters.")
    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
