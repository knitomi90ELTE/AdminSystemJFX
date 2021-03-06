package hu.kniznertamas.adminsystem.db.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "balance")
public class BalanceEntity extends PersistentEntity {

    @Column(name = "netto")
    Integer netto;
    @Column(name = "brutto")
    Integer brutto;
    @Column(name = "afa")
    Integer afa;
    @Column(name = "afa_value")
    Integer afaValue;
    @Column(name = "created")
    Date created;
    @Column(name = "completed")
    Date completed;
    @Column(name = "status_id")
    Integer statusId;
    @Column(name = "model_name")
    String modelName;
    @Column(name = "model_id")
    Integer modelId;
    @Column(name = "cash")
    Boolean cash;
    @Column(name = "note")
    String note;
    
    @Basic
    @Column(name = "netto")
    public Integer getNetto() {
        return netto;
    }

    public void setNetto(Integer netto) {
        this.netto = netto;
    }

    @Basic
    @Column(name = "brutto")
    public Integer getBrutto() {
        return brutto;
    }

    public void setBrutto(Integer brutto) {
        this.brutto = brutto;
    }

    @Basic
    @Column(name = "afa")
    public Integer getAfa() {
        return afa;
    }

    public void setAfa(Integer afa) {
        this.afa = afa;
    }

    @Basic
    @Column(name = "afa_value")
    public Integer getAfaValue() {
        return afaValue;
    }

    public void setAfaValue(Integer afaValue) {
        this.afaValue = afaValue;
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
    @Column(name = "completed")
    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    @Basic
    @Column(name = "status_id")
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Basic
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "cash")
    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
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
        if (!(o instanceof BalanceEntity)) return false;

        BalanceEntity that = (BalanceEntity) o;

        if (!netto.equals(that.netto)) return false;
        if (!brutto.equals(that.brutto)) return false;
        if (!afa.equals(that.afa)) return false;
        if (!afaValue.equals(that.afaValue)) return false;
        if (!created.equals(that.created)) return false;
        //noinspection SimplifiableIfStatement
        if (completed != null ? !completed.equals(that.completed) : that.completed != null) return false;
        return statusId.equals(that.statusId) && (modelName != null ? modelName.equals(that.modelName) : that.modelName == null && (modelId != null ? modelId.equals(that.modelId) : that.modelId == null && cash.equals(that.cash) && (note != null ? note.equals(that.note) : that.note == null)));

    }

    @Override
    public int hashCode() {
        int result = netto.hashCode();
        result = 31 * result + brutto.hashCode();
        result = 31 * result + afa.hashCode();
        result = 31 * result + afaValue.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
        result = 31 * result + statusId.hashCode();
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + cash.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BalanceEntity{" +
                "netto=" + netto +
                ", brutto=" + brutto +
                ", afa=" + afa +
                ", afaValue=" + afaValue +
                ", created=" + created +
                ", created=" + completed +
                ", statusId=" + statusId +
                ", modelName='" + modelName + '\'' +
                ", modelId=" + modelId +
                ", cash=" + cash +
                ", note='" + note + '\'' +
                '}';
    }
}