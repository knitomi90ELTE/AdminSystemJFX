package hu.kniznertamas.adminsystem.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class StatusEntity extends PersistentEntity {

    private String name;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;

        //noinspection NumberEquality
        return id == that.id && (name != null ? name.equals(that.name) : that.name == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex){
            case 0:
                return id;
            case 1:
                return name;
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
                setName((String) value);
                break;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
