package projectdefence.models.serviceModels;

public class MeasurementAddServiceModel {
    private String content;
    private String stage;
    private Integer age;
    private String disease;

    public MeasurementAddServiceModel() {
    }

    public String getContent() {
        return content;
    }

    public MeasurementAddServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public MeasurementAddServiceModel setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public MeasurementAddServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public MeasurementAddServiceModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }
}
