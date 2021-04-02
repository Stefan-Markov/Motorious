package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static projectdefence.messages.ExceptionMessages.*;

public class TreatmentAddBindingModel {
    private String username;
    private String disease;
    private String goal;
    private Double duration;
    private Integer visits;
    private String content;
    private String createdBy;
    private String KtFullName;

    public TreatmentAddBindingModel() {
    }


    public String getKtFullName() {
        return KtFullName;
    }

    public TreatmentAddBindingModel setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 2, max = 50, message = LENGTH_BETWEEN_2_50_CHARACTERS)
    public String getUsername() {
        return username;
    }

    public TreatmentAddBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 2, max = 200, message = INVALID_DISEASE)
    public String getDisease() {
        return disease;
    }

    public TreatmentAddBindingModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 2, max = 200, message = INVALID_GOAL)
    public String getGoal() {
        return goal;
    }

    public TreatmentAddBindingModel setGoal(String goal) {
        this.goal = goal;
        return this;
    }

    @NotNull(message = EMPTY_FIELD)
    @Min(value = 1, message = INVALID_MIN_VALUE_1)
    @Max(value = 100, message = INVALID_MAX_VALUE_100)
    public Double getDuration() {
        return duration;
    }

    public TreatmentAddBindingModel setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    @NotNull(message = EMPTY_FIELD)
    @Min(value = 1, message = INVALID_MIN_VALUE_1)
    @Max(value = 100, message = INVALID_MAX_VALUE_100)
    public Integer getVisits() {
        return visits;
    }

    public TreatmentAddBindingModel setVisits(Integer visits) {
        this.visits = visits;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 6, max = 2000, message = INVALID_CONTENT)
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
