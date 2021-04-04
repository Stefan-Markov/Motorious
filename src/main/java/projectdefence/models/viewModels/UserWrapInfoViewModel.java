package projectdefence.models.viewModels;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class UserWrapInfoViewModel {

    private String firstName;
    private String lastName;
    private String username;
    private String title;
    private String email;
    private LocalDateTime createdDate;
    private String imageUrl;

    public UserWrapInfoViewModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserWrapInfoViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserWrapInfoViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserWrapInfoViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserWrapInfoViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public UserWrapInfoViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserWrapInfoViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public UserWrapInfoViewModel setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
