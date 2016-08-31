package hu.kniznertamas.adminsystem.db.dao;

import java.util.List;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;

public interface GenericDao<T extends PersistentEntity> {

    void create(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> findAll();
    T findById(Integer id);
}
