package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.UserViewController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;
import java.time.LocalDate;

interface IMediateControllers {
    void registerControllerUser(UserViewController controller);
    void registerControllerUploadTable(UploadTableController controller);
    void registerControllerBalanceTable(BalanceTableController controller);
    void registerControlerProjects(ProjectViewController controller);
    void registerControlerHoursTable(HoursTableController controller);
    void registerControlerFinancesTable(FinancesTableController controller);

    //Alkalmazottak nézet frissítése
    void loadUserDataToController(UsersEntity usersEntity);
    //Munkák nézet frissítése
    void loadProjectDataToController(ProjectsEntity projectsEntity);
    //Napi nézet frissítése
    void refreshDailyTableData(LocalDate currentDate);

}
