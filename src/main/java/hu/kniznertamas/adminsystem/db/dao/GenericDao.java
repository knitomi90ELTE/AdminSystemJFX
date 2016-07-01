package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;

import java.util.List;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public interface GenericDao<T extends PersistentEntity> {

    public void create(T entity);

    public void update(T entity);

    public void delete(T entity);

    public List<T> findAll();

    public T findById(Integer id);

}
