package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;
import static projectdefence.messages.ExceptionMessages.*;

import javax.validation.constraints.NotBlank;

public class UserChangeRoleBindingModel {

    private String username;

    public UserChangeRoleBindingModel() {

    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
    public String getUsername() {
        return username;
    }

    public UserChangeRoleBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
