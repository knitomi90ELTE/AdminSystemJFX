package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import java.time.LocalDate;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.MainController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.OpenItemsViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.UserViewController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;
import javafx.scene.layout.StackPane;

interface IMediateControllers {
    void registerControllerUser(UserViewController controller);
    void registerControllerUploadTable(UploadTableController controller);
    void registerControllerBalanceTable(BalanceTableController controller);
    void registerControllerProjects(ProjectViewController controller);
    void registerControllerHoursTable(HoursTableController controller);
    void registerControllerFinancesTable(FinancesTableController controller);
    void registerControllerOpenItemsController(OpenItemsViewController controller);
    void registerControllerMainController(MainController controller);

    //Alkalmazottak nézet frissítése
    void loadUserDataToController(UsersEntity usersEntity);
    //Munkák nézet frissítése
    void loadProjectDataToController(ProjectsEntity projectsEntity);
    //Napi nézet frissítése
    void refreshDailyTableData(LocalDate currentDate);

    void registerControllerDailyView(DailyViewController dailyViewController);

    void refreshOpenItemsTable();

    LocalDate getCurrentDate();
    
    void showBasicView();
    void showNewDataView();
    
    StackPane getRoot();


}
