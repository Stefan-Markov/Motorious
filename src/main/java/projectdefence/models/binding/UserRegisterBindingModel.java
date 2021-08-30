package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectdefence.validators.fieldMatch.FieldMatch;
import projectdefence.validators.password.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static projectdefence.messages.ExceptionMessages.*;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    @ValidPassword
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


    @Length(min = 6, max = 50, message = LENGTH_BETWEEN_6_50_CHARACTERS)
    @NotBlank(message = EMPTY_FIELD)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
    @NotBlank(message = EMPTY_FIELD)
    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
    @NotBlank(message = EMPTY_FIELD)
    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Length(min = 3, max = 50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = EMPTY_FIELD)
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email(message = INVALID_EMAIL)
    @NotBlank(message = EMPTY_FIELD)
    @Length(max = 150, message = INVALID_EMAIL)
    // More advance check
    //  @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
    //    message = "Enter a valid email address!")
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(min = 6, max = 50, message = LENGTH_BETWEEN_6_50_CHARACTERS)
    @NotBlank(message = EMPTY_FIELD)
    // More advance check
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}$",
    //            message = "Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character!")
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
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
