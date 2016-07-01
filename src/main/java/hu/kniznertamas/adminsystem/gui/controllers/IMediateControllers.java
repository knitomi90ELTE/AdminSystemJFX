package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 02..
 */
public interface IMediateControllers {
    void registerControllerMenu(MenuBarController controller);
    void registerControllerUser(UserViewController controller);
    void loadUserDataToController(UsersEntity usersEntity);
}
