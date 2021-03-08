package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserRegisterBindingModel {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String title;

    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Please fill the information.")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Length(min = 3, max = 50, message = "Must contains minimum three characters.")
    @NotEmpty(message = "")
    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Length(min = 3, max = 50, message = "Must contains minimum three characters.")
    @NotEmpty(message = "")
    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Length(min = 3, max = 50, message = "Must contains minimum three characters.")
    @NotEmpty(message = "")
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email(message = "Enter valid email address.")
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(min = 6, max = 50, message = "Must contains minimum six characters.")
    @NotEmpty(message = "")
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public UserRegisterBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSamePasswords() {
        return checkPassword();
    }

    private boolean checkPassword() {
        return this.getPassword().equals(this.getConfirmPassword());
    }
}
