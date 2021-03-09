package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class AddBlogBindingModel {

    private String title;
    private String content;

    public AddBlogBindingModel() {
    }

    @NotBlank(message = "Please enter the title.")
    @Length(min = 6, message = "Enter minimum six characters.")
    public String getTitle() {
        return title;
    }

    public AddBlogBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @NotBlank(message = "Please enter the content.")
    @Length(min = 6, message = "Enter minimum six characters.")
    public String getContent() {
        return content;
    }

    public AddBlogBindingModel setContent(String content) {
        this.content = content;
        return this;
    }
}
