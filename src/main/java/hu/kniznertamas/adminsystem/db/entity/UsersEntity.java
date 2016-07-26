package hu.kniznertamas.adminsystem.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "adminsystem_test", catalog = "")
public class UsersEntity extends PersistentEntity {

    private String name;
    private Integer wage;
    private String note;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "wage")
    public Integer getWage() {
        return wage;
    }

    public void setWage(Integer wage) {
        this.wage = wage;
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

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (wage != that.wage) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + wage;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex){
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return wage;
            case 3:
                return note;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setId((Integer) value);
                break;
            case 1:
                setName((String) value);
                break;
            case 2:
                setWage((Integer) value);
                break;
            case 3:
                setNote((String) value);
                break;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
