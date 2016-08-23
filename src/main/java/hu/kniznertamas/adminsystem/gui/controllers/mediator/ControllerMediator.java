package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.UserViewController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;
import javafx.application.Platform;

import java.time.LocalDate;

public class ControllerMediator implements IMediateControllers {

    private UserViewController userViewController;
    private UploadTableController uploadTableController;
    private BalanceTableController balanceTableController;
    private ProjectViewController projectViewController;
    private FinancesTableController financesTableController;
    private HoursTableController hoursTableController;

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
    public void registerControlerProjects(ProjectViewController controller) {
        projectViewController = controller;
    }

    @Override
    public void registerControlerHoursTable(HoursTableController controller) {
        hoursTableController = controller;
    }

    @Override
    public void registerControlerFinancesTable(FinancesTableController controller) {
        financesTableController = controller;
    }

    @Override
    public void loadUserDataToController(UsersEntity usersEntity) {
        userViewController.loadUserData(usersEntity);
    }

    @Override
    public void loadProjectDataToController(ProjectsEntity projectsEntity) {
        Platform.runLater(() ->  projectViewController.loadProjectData(projectsEntity));
        Platform.runLater(() -> hoursTableController.refreshTableData(projectsEntity));
        Platform.runLater(() -> financesTableController.refreshTableData(projectsEntity));
    }

    @Override
    public void refreshDailyTableData(LocalDate currentDate) {
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
