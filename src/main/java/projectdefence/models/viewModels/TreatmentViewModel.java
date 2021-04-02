package projectdefence.models.viewModels;


import java.time.LocalDate;

public class TreatmentViewModel {

    private String id;

    public String getId() {
        return id;
    }

    public TreatmentViewModel setId(String id) {
        this.id = id;
        return this;
    }

    private String disease;
    private LocalDate dateAdded;
    private String createdBy;
    private Double duration;
    private Integer visits;
    private String content;
    private String goal;
    private String KtFullName;

    public String getKtFullName() {
        return KtFullName;
    }

    public TreatmentViewModel setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }

    public TreatmentViewModel() {
    }

    public String getDisease() {
        return disease;
    }

    public TreatmentViewModel setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public TreatmentViewModel setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TreatmentViewModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public TreatmentViewModel setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public Integer getVisits() {
        return visits;
    }

    public TreatmentViewModel setVisits(Integer visits) {
        this.visits = visits;
        return this;
    }

    public String getContent() {
        return content;
    }

    public TreatmentViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getGoal() {
        return goal;
    }

    public TreatmentViewModel setGoal(String goal) {
        this.goal = goal;
        return this;
    }
}
