package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class DefaultProjectsDao extends DefaultDao<ProjectsEntity> {

    public DefaultProjectsDao(Class<ProjectsEntity> CLASS) {
        super(CLASS);
    }
}
