package projectdefence.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "measurements")
public class Measurement extends BaseEntity {

    private LocalDate date;
    private String content;
    private String stage;
    private User user;
    private Integer age;
    private String disease;
    private String createdBy;
    private String KtFullName;

    @Column(name = "kinesiotherapist_full_name", nullable = false)
    public String getKtFullName() {
        return KtFullName;
    }

    public Measurement setKtFullName(String ktFullName) {
        KtFullName = ktFullName;
        return this;
    }

    @Column(name = "created_by", nullable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public Measurement setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public Measurement setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Column(name = "disease", nullable = false)
    public String getDisease() {
        return disease;
    }

    public Measurement setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    @Column(name = "stage", nullable = false)
    public String getStage() {
        return stage;
    }

    public Measurement setStage(String stage) {
        this.stage = stage;
        return this;
    }

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public Measurement setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    public String getContent() {
        return content;
    }

    public Measurement setContent(String content) {
        this.content = content;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public Measurement setUser(User user) {
        this.user = user;
        return this;
    }
}
