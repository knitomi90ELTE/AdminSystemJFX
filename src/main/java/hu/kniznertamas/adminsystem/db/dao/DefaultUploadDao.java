package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.UploadEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class DefaultUploadDao extends DefaultDao<UploadEntity> {

    public DefaultUploadDao(Class<UploadEntity> CLASS) {
        super(CLASS);
    }
}
