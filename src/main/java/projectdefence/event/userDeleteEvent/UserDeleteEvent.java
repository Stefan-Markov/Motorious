package projectdefence.event.userDeleteEvent;

import org.springframework.context.ApplicationEvent;

public class UserDeleteEvent extends ApplicationEvent {

    private String firstName;
    private String lastName;
    private String username;

    public UserDeleteEvent(String firstName, String lastName, String username) {
        super(username);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDeleteEvent{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDeleteEvent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDeleteEvent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDeleteEvent setUsername(String username) {
        this.username = username;
        return this;
    }
}
