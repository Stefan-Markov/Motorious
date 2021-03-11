package projectdefence.models.viewModels;


import java.time.LocalDate;

public class MeasurementByUserNameViewModel {
    private LocalDate date;
    private String content;
    private String stage;
    private Integer age;
    private String disease;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public MeasurementByUserNameViewModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MeasurementByUserNameViewModel() {
    }

    public LocalDate getDate() {
        return date;
    }

    public MeasurementByUserNameViewModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MeasurementByUserNameViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public MeasurementByUserNameViewModel setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public MeasurementByUserNameViewModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public MeasurementByUserNameViewModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }
}
