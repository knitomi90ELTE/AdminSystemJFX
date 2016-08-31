package hu.kniznertamas.adminsystem.db.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "upload")
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
