package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import java.util.List;

public interface GenericDao<T extends PersistentEntity> {

    void create(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> findAll();
    T findById(Integer id);
}
