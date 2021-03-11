package projectdefence.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "treatments")
public class Treatment extends BaseEntity {

    private String disease;
    private LocalDate dateAdded;
    private String createdBy;
    private Double duration;
    private Integer visits;
    private String content;
    private User user;
    private String goal;

    public Treatment() {
    }

    @Column(name = "disease", nullable = false)
    public String getDisease() {
        return disease;
    }

    public Treatment setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    @Column(name = "date_added", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Treatment setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    @Column(name = "created_by", nullable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public Treatment setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Column(name = "duration", nullable = false)
    public Double getDuration() {
        return duration;
    }

    public Treatment setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    @Column(name = "visits", nullable = false)
    public Integer getVisits() {
        return visits;
    }

    public Treatment setVisits(Integer visits) {
        this.visits = visits;
        return this;
    }

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public Treatment setContent(String content) {
        this.content = content;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public Treatment setUser(User user) {
        this.user = user;
        return this;
    }

    @Column(name = "goal", nullable = false)
    public String getGoal() {
        return goal;
    }

    public Treatment setGoal(String goal) {
        this.goal = goal;
        return this;
    }
}
