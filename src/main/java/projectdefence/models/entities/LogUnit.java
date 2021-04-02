package projectdefence.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogUnit extends BaseEntity {

    private User user;

    @Column(name = "action", nullable = false)
    private String action;
    @Column(name = "date_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTime;

    public LogUnit() {
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public LogUnit setUser(User user) {
        this.user = user;
        return this;
    }


    public String getAction() {
        return action;
    }

    public LogUnit setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LogUnit setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
