package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static projectdefence.messages.ExceptionMessages.*;

public class AddBlogBindingModel {

    private String title;
    private String content;

    public AddBlogBindingModel() {
    }

    @NotBlank(message = EMPTY_BLOG_TITLE)
    @Length(min = 6, max = 100, message = LENGTH_BETWEEN_6_100_CHARACTERS)
    public String getTitle() {
        return title;
    }

    public AddBlogBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @NotBlank(message = EMPTY_CONTENT)
    @Length(min = 6, max = 2000, message = INVALID_CONTENT)
    public String getContent() {
        return content;
    }

    public AddBlogBindingModel setContent(String content) {
        this.content = content;
        return this;
    }
}
