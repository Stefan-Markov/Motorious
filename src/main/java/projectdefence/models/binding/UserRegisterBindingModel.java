package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectdefence.messages.ExceptionMessages;
import projectdefence.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String title;
    private MultipartFile image;

    public UserRegisterBindingModel() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public UserRegisterBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }


    @Length(min = 6, max = 50, message = ExceptionMessages.LENGTH_BETWEEN_6_50_CHARACTERS)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Length(min = 3, max = 50, message = ExceptionMessages.LENGTH_BETWEEN_3_50_CHARACTERS)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Length(min = 3, max = 50, message = ExceptionMessages.LENGTH_BETWEEN_3_50_CHARACTERS)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Length(min = 3, max = 50, message = ExceptionMessages.INVALID_USERNAME_LENGTH)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email(message = ExceptionMessages.INVALID_EMAIL)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(min = 6, max = 50, message = ExceptionMessages.LENGTH_BETWEEN_6_50_CHARACTERS)
    @NotBlank(message = ExceptionMessages.EMPTY_FIELD)
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
