package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class DefaultUsersDao extends DefaultDao<UsersEntity> {

    public DefaultUsersDao(Class<UsersEntity> CLASS) {
        super(CLASS);
    }



}
