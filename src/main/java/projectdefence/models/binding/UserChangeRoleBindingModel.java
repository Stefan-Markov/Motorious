package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

public class UserChangeRoleBindingModel {

    private String username;



    public UserChangeRoleBindingModel() {

    }



    @Length(min = 3, max = 50, message = "Must contains minimum three characters.")
    public String getUsername() {
        return username;
    }

    public UserChangeRoleBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
