package projectdefence.models.viewModels;


import java.time.LocalDate;

public class MeasurementViewModel {

    private String id;
    private LocalDate date;
    private String content;
    private String stage;
    private Integer age;
    private String disease;
    private String createdBy;
    private String KtFullName;

    public MeasurementViewModel() {
    }

    public String getId() {
        return id;
    }

    public MeasurementViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getKtFullName() {
        return KtFullName;
    }

    public MeasurementViewModel setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MeasurementViewModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public MeasurementViewModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MeasurementViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public MeasurementViewModel setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public MeasurementViewModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public MeasurementViewModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }
}
