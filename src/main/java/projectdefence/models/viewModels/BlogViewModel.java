package projectdefence.models.viewModels;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BlogViewModel {

    private Long id;
    private String title;
    private String content;
    private Date date;
    private String author;

    public BlogViewModel() {
    }

    public Long getId() {
        return id;
    }

    public BlogViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlogViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public BlogViewModel setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BlogViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }
}
