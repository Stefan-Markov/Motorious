package projectdefence.models.serviceModels;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class UserServiceModel {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private String title;
    private MultipartFile image;
    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
    }

    public String getId() {
        return id;
    }

    public UserServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public UserServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public UserServiceModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public UserServiceModel setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
        return this;
    }
}
