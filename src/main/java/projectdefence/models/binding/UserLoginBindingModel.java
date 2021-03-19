package projectdefence.models.binding;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static projectdefence.messages.ExceptionMessages.*;

public class UserLoginBindingModel {

    private String username;
    private String password;

    public UserLoginBindingModel() {
    }

    @NotBlank(message = EMPTY_FIELD)
    @Size(min = 3, max = 50, message = INVALID_USERNAME_LENGTH)
    public String getUsername() {
        return username;
    }

    public UserLoginBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Size(min = 6, max = 50, message = LENGTH_BETWEEN_6_50_CHARACTERS)
    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
