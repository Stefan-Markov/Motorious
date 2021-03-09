package projectdefence.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Table
@Entity
public class Measurement extends BaseEntity {

    private LocalDate date;
    private String title;
    private String content;
    private User user;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public Measurement setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public Measurement setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "content", columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public Measurement setContent(String content) {
        this.content = content;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public Measurement setUser(User user) {
        this.user = user;
        return this;
    }
}
