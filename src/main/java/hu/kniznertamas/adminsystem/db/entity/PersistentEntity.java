package hu.kniznertamas.adminsystem.db.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public abstract Object get(int columnIndex);

    public abstract void set(int columnIndex, Object value);

}
