package projectdefence.models.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private String title;
    private String imageUrl;
    private List<Measurement> measurements;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Set<Role> authorities;

    public User() {
    }

    @OneToMany(mappedBy = "user")
    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public User setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Column(name = "created_info", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Column(name = "title",nullable = false)
    public String getTitle() {
        return title;
    }

    public User setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "profile_pic")
    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


    @Column(name = "firs_name", nullable = false)
    @Length(min = 3, message = "Must contain minimum three characters.")
    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(name = "last_name", nullable = false)
    @Length(min = 3, message = "Must contain minimum three characters.")
    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 3, message = "Must contain minimum three characters.")
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
