package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.MenuBarController;
import hu.kniznertamas.adminsystem.gui.controllers.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.UserViewController;

import java.time.LocalDate;

interface IMediateControllers {
    void registerControllerMenu(MenuBarController controller);
    void registerControllerUser(UserViewController controller);
    void registerControllerUploadTable(UploadTableController controller);
    void registerControllerBalanceTable(BalanceTableController controller);

    void loadUserDataToController(UsersEntity usersEntity);
    void refreshTableData(LocalDate currentDate);
}
