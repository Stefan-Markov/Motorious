package projectdefence.models.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseItemsEntity {
    private Long id;

    public BaseItemsEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    public Long getId() {
        return id;
    }

    public BaseItemsEntity setId(Long id) {
        this.id = id;
        return this;
    }
}
