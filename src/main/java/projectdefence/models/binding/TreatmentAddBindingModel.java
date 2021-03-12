package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TreatmentAddBindingModel {
    private String username;
    private String disease;
    private String goal;
    private Double duration;
    private Integer visits;
    private String content;
    private String createdBy;

    @NotBlank(message = "")
    @Length(min = 2, max = 50, message = "Enter valid username.")
    public String getUsername() {
        return username;
    }

    public TreatmentAddBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = "")
    @Length(min = 2, max = 200, message = "Enter valid disease.")
    public String getDisease() {
        return disease;
    }

    public TreatmentAddBindingModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    @NotBlank(message = "Must contains info.")
    @Length(min = 2, max = 200, message = "Enter valid goal.")
    public String getGoal() {
        return goal;
    }

    public TreatmentAddBindingModel setGoal(String goal) {
        this.goal = goal;
        return this;
    }

    @NotNull(message = "Must contains info.")
    @Min(value = 1, message = "Enter valid duration.")
    public Double getDuration() {
        return duration;
    }

    public TreatmentAddBindingModel setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    @NotNull(message = "Must contains info.")
    @Min(value = 1, message = "Enter valid visits.")
    public Integer getVisits() {
        return visits;
    }

    public TreatmentAddBindingModel setVisits(Integer visits) {
        this.visits = visits;
        return this;
    }

    @NotBlank(message = "Must contains info.")
    @Length(min = 3, message = "Enter content for the user.")
    public String getContent() {
        return content;
    }

    public TreatmentAddBindingModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TreatmentAddBindingModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
