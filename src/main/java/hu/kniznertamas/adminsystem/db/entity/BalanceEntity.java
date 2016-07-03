package hu.kniznertamas.adminsystem.db.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "balance", schema = "adminsystem_test", catalog = "")
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
        if (o == null || getClass() != o.getClass()) return false;

        BalanceEntity that = (BalanceEntity) o;

        if (id != that.id) return false;
        if (netto != that.netto) return false;
        if (brutto != that.brutto) return false;
        if (afa != that.afa) return false;
        if (afaValue != that.afaValue) return false;
        if (statusId != that.statusId) return false;
        if (cash != that.cash) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + netto;
        result = 31 * result + brutto;
        result = 31 * result + afa;
        result = 31 * result + afaValue;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + statusId;
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + ((cash) ? 1 : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex){
            case 0:
                return id;
            case 1:
                return netto;
            case 2:
                return brutto;
            case 3:
                return afa;
            case 4:
                return afaValue;
            case 5:
                return created;
            case 6:
                return statusId;
            case 7:
                return modelName;
            case 8:
                return modelId;
            case 9:
                return cash;
            case 10:
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
                setNetto((Integer) value);
                break;
            case 2:
                setBrutto((Integer) value);
                break;
            case 3:
                setAfa((Integer) value);
                break;
            case 4:
                setAfaValue((Integer) value);
                break;
            case 5:
                setCreated((Date) value);
                break;
            case 6:
                setStatusId((Integer) value);
                break;
            case 7:
                setModelName((String) value);
                break;
            case 8:
                setModelId((Integer) value);
                break;
            case 9:
                setCash((Boolean) value);
                break;
            case 10:
                setNote((String) value);
                break;
        }
    }

    @Override
    public String toString() {
        return "BalanceEntity{" +
                "netto=" + netto +
                ", brutto=" + brutto +
                ", afa=" + afa +
                ", afaValue=" + afaValue +
                ", created=" + created +
                ", statusId=" + statusId +
                ", modelName='" + modelName + '\'' +
                ", modelId=" + modelId +
                ", cash=" + cash +
                ", note='" + note + '\'' +
                '}';
    }
}