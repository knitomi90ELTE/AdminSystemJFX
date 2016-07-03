package hu.kniznertamas.adminsystem.db.entity;

public class ExtendedUploadEntity extends UploadEntity {

    private String user_name;
    private String project_name;

    public ExtendedUploadEntity() {
    }

    public ExtendedUploadEntity(UploadEntity ue) {
        this.id = ue.getId();
        this.userId = ue.getUserId();
        this.projectId = ue.getProjectId();
        this.hour = ue.getHour();
        this.created = ue.getCreated();
        this.note = ue.getNote();
    }

    public String getUser_name() { return user_name; }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
