package projectdefence.event.userRegisterEvent;

import org.springframework.context.ApplicationEvent;


public class UserRegisterEvent extends ApplicationEvent {

    private String firstName;
    private String lastName;
    private String username;


    public UserRegisterEvent(String firstName, String lastName, String username) {
        super(username);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;

    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterEvent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterEvent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    @Override
    public String toString() {
        return "UserRegisterEvent{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterEvent setUsername(String username) {
        this.username = username;
        return this;
    }
}
