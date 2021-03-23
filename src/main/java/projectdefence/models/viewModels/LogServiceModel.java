package projectdefence.models.viewModels;

import java.time.LocalDateTime;

public class LogServiceModel {
    private String id;
    private String user;
    private String action;
    private LocalDateTime dateTime;

    public LogServiceModel() {
    }

    public String getId() {
        return id;
    }

    public LogServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public LogServiceModel setUser(String user) {
        this.user = user;
        return this;
    }


    public String getAction() {
        return action;
    }

    public LogServiceModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LogServiceModel setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
