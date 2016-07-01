package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;

/**
 * Created by Knizner Tamás on 2016. 07. 02..
 */
public class ControllerMediator implements IMediateControllers {

    private MenuBarController menuBarController;
    private UserViewController userViewController;

    @Override
    public void registerControllerMenu(MenuBarController controller) {
        menuBarController = controller;
    }

    @Override
    public void registerControllerUser(UserViewController controller) {
        userViewController = controller;
    }

    @Override
    public void loadUserDataToController(UsersEntity usersEntity) {
        userViewController.loadUserData(usersEntity);
    }

    private ControllerMediator() {}

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }
}
