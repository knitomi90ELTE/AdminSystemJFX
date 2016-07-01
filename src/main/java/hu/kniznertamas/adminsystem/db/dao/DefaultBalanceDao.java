package hu.kniznertamas.adminsystem.db.dao;

import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class DefaultBalanceDao extends DefaultDao<BalanceEntity>{

    public DefaultBalanceDao(Class<BalanceEntity> CLASS) {
        super(CLASS);
    }
}
