package projectdefence.models.serviceModels;

public class AddBlogServiceModel {

    private Long id;
    private String title;
    private String content;

    public AddBlogServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public AddBlogServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AddBlogServiceModel setContent(String content) {
        this.content = content;
        return this;
    }
}
