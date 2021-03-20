package projectdefence.models.binding;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

import static projectdefence.messages.ExceptionMessages.*;

public class MeasurementAddBindingModel {

    private String content;
    private String stage;
    private Integer age;
    private String disease;
    private String username;
    private String KtFullName;

    public MeasurementAddBindingModel() {
    }

    public String getKtFullName() {
        return KtFullName;
    }

    public MeasurementAddBindingModel setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Size(min = 3, max = 50, message = INVALID_USERNAME_LENGTH)
    public String getUsername() {
        return username;
    }

    public MeasurementAddBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 6, max = 2000, message = INVALID_CONTENT)
    public String getContent() {
        return content;
    }

    public MeasurementAddBindingModel setContent(String content) {
        this.content = content;
        return this;
    }


    @NotBlank(message = EMPTY_FIELD)
    public String getStage() {
        return stage;
    }

    public MeasurementAddBindingModel setStage(String stage) {
        this.stage = stage;
        return this;
    }

    @NotNull(message = EMPTY_AGE)
    @Min(value = 1, message = INVALID_AGE_MIN_1)
    @Max(value = 140, message = INVALID_AGE_MAX_140)
    public Integer getAge() {
        return age;
    }

    public MeasurementAddBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 2, max = 200, message = INVALID_DISEASE)
    public String getDisease() {
        return disease;
    }

    public MeasurementAddBindingModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }
}
