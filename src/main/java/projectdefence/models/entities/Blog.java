package projectdefence.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "blog")
public class Blog extends BaseItemsEntity {

    private String title;
    private String content;
    private LocalDate date;
    private String author;
    private List<User> user;

    public Blog() {
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public Blog setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "content", columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public Blog setContent(String content) {
        this.content = content;
        return this;
    }

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public Blog setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public Blog setAuthor(String author) {
        this.author = author;
        return this;
    }

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_blogs",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"))
    public List<User> getUser() {
        return user;
    }

    public Blog setUser(List<User> user) {
        this.user = user;
        return this;
    }
}
