package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.MenuBarController;
import hu.kniznertamas.adminsystem.gui.controllers.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.UserViewController;

import java.time.LocalDate;

public class ControllerMediator implements IMediateControllers {

    private MenuBarController menuBarController;
    private UserViewController userViewController;
    private UploadTableController uploadTableController;
    private BalanceTableController balanceTableController;

    @Override
    public void registerControllerMenu(MenuBarController controller) {
        menuBarController = controller;
    }

    @Override
    public void registerControllerUser(UserViewController controller) {
        userViewController = controller;
    }

    @Override
    public void registerControllerUploadTable(UploadTableController controller) {
        uploadTableController = controller;
    }

    @Override
    public void registerControllerBalanceTable(BalanceTableController controller) {
        balanceTableController = controller;
    }

    @Override
    public void loadUserDataToController(UsersEntity usersEntity) {
        userViewController.loadUserData(usersEntity);
    }

    @Override
    public void refreshTableData(LocalDate currentDate) {
        new Thread() {
            @Override
            public void run() {
                uploadTableController.refreshTableData(currentDate);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                balanceTableController.refreshTableData(currentDate);
            }
        }.start();
    }

    private ControllerMediator() {
    }

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }
}
