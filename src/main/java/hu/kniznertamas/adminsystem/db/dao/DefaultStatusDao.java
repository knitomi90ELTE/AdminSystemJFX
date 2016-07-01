package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.StatusEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class DefaultStatusDao extends DefaultDao<StatusEntity> {

    public DefaultStatusDao(Class<StatusEntity> CLASS) {
        super(CLASS);
    }
}
