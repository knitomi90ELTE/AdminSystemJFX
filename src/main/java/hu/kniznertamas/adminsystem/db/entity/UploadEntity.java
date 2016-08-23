package hu.kniznertamas.adminsystem.db.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "upload", schema = "kniznerasztalos")
public class UploadEntity extends PersistentEntity {

    @Column(name = "user_id")
    Integer userId;
    @Column(name = "project_id")
    Integer projectId;
    @Column(name = "created")
    Date created;
    @Column(name = "hour")
    Double hour;
    @Column(name = "note")
    String note;

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "hour")
    public Double getHour() {
        return hour;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadEntity that = (UploadEntity) o;

        //noinspection NumberEquality
        if (id != that.id) return false;
        //noinspection NumberEquality
        if (userId != that.userId) return false;
        //noinspection SimplifiableIfStatement,NumberEquality
        if (projectId != that.projectId) return false;
        return Double.compare(that.hour, hour) == 0 && (created != null ? created.equals(that.created) : that.created == null && (note != null ? note.equals(that.note) : that.note == null));

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + userId;
        result = 31 * result + projectId;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        temp = Double.doubleToLongBits(hour);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex){
            case 0:
                return id;
            case 1:
                return userId;
            case 2:
                return projectId;
            case 3:
                return created;
            case 4:
                return hour;
            case 5:
                return note;
            default:
                return null;

        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex){
            case 0:
                setId((Integer) value);
                break;
            case 1:
                setUserId((Integer) value);
                break;
            case 2:
                setProjectId((Integer) value);
                break;
            case 3:
                setCreated((Date) value);
                break;
            case 4:
                setHour((Double) value);
                break;
            case 5:
                setNote((String ) value);
                break;
        }
    }

    @Override
    public String toString() {
        return "UploadEntity{" +
                "userId=" + userId +
                ", projectId=" + projectId +
                ", created=" + created +
                ", hour=" + hour +
                ", note='" + note + '\'' +
                '}';
    }
}
