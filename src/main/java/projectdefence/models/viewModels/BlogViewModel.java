package projectdefence.models.viewModels;

import java.time.LocalDate;

public class BlogViewModel {

    private Long id;
    private String title;
    private String content;
    private LocalDate date;
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

    public LocalDate getDate() {
        return date;
    }

    public BlogViewModel setDate(LocalDate date) {
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
