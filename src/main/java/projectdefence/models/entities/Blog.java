package projectdefence.models.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "blog")
public class Blog extends BaseItemsEntity {

    private String title;
    private String content;
    private Date date;
    private String author;
    private User user;

    public Blog() {
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "title", nullable = false)
    @NotNull
    public String getTitle() {
        return title;
    }

    public Blog setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @Access(AccessType.PROPERTY)
    @NotNull
    public String getContent() {
        return content;
    }

    public Blog setContent(String content) {
        this.content = content;
        return this;
    }

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public Blog setDate(Date date) {
        this.date = date;
        return this;
    }

    @Column(name = "author", nullable = false)
    @NotNull
    public String getAuthor() {
        return author;
    }

    public Blog setAuthor(String author) {
        this.author = author;
        return this;
    }

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "users_blogs",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"))
    public User getUser() {
        return user;
    }

    public Blog setUser(User user) {
        this.user = user;
        return this;
    }
}
