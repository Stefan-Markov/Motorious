package projectdefence.models.binding;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class MeasurementAddBindingModel {

    private String content;
    private String stage;
    private Integer age;
    private String disease;
    private String username;

    public MeasurementAddBindingModel() {
    }

    @NotBlank(message = "Should be not empty")
    @Size(min = 3, max = 50, message = "Username has minimum three characters")
    public String getUsername() {
        return username;
    }

    public MeasurementAddBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotBlank(message = "Enter content for the measurement.")
    @Length(min = 6, max = 1000, message = "Must contains minimum six characters.")
    public String getContent() {
        return content;
    }

    public MeasurementAddBindingModel setContent(String content) {
        this.content = content;
        return this;
    }


    @NotBlank(message = "Must contain stage")
    public String getStage() {
        return stage;
    }

    public MeasurementAddBindingModel setStage(String stage) {
        this.stage = stage;
        return this;
    }

    @NotNull(message = "Must contain age.")
    @Min(value = 1, message = "Age must be greater than one.")
    @Max(value = 140, message = "Age must be lower than one hundred and forty.")
    public Integer getAge() {
        return age;
    }

    public MeasurementAddBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @NotBlank(message = "Must contains information.")
    public String getDisease() {
        return disease;
    }

    public MeasurementAddBindingModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }
}
