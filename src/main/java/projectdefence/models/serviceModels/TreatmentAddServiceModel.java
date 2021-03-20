package projectdefence.models.serviceModels;

public class TreatmentAddServiceModel {
    private String id;
    private String username;
    private String disease;
    private String goal;
    private Double duration;
    private Integer visits;
    private String content;
    private String createdBy;
    private String KtFullName;

    public TreatmentAddServiceModel() {
    }

    public String getId() {
        return id;
    }

    public TreatmentAddServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TreatmentAddServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public TreatmentAddServiceModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    public String getGoal() {
        return goal;
    }

    public TreatmentAddServiceModel setGoal(String goal) {
        this.goal = goal;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public TreatmentAddServiceModel setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public Integer getVisits() {
        return visits;
    }

    public TreatmentAddServiceModel setVisits(Integer visits) {
        this.visits = visits;
        return this;
    }

    public String getContent() {
        return content;
    }

    public TreatmentAddServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TreatmentAddServiceModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getKtFullName() {
        return KtFullName;
    }

    public TreatmentAddServiceModel setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }
}
