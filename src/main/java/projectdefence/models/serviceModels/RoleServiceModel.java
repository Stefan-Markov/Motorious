package projectdefence.models.serviceModels;

public class RoleServiceModel {

    private String id;

    private String authority;

    public RoleServiceModel() {
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getId() {
        return id;
    }

    public RoleServiceModel setId(String id) {
        this.id = id;
        return this;
    }
}
