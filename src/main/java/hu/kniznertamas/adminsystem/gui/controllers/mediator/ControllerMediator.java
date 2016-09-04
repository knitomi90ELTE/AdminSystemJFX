package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import java.time.LocalDate;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.MainController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.FinancesController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.OpenItemsViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.UserViewController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;

public class ControllerMediator implements IMediateControllers {

    private UserViewController userViewController;
    private UploadTableController uploadTableController;
    private BalanceTableController balanceTableController;
    private ProjectViewController projectViewController;
    private FinancesTableController financesTableController;
    private HoursTableController hoursTableController;
    private DailyViewController dailyViewController;
    private OpenItemsViewController openItemsViewController;
    private FinancesController financesController;
    private MainController mainController;

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
    public void registerControllerProjects(ProjectViewController controller) {
        projectViewController = controller;
    }

    @Override
    public void registerControllerHoursTable(HoursTableController controller) {
        hoursTableController = controller;
    }

    @Override
    public void registerControllerFinancesTable(FinancesTableController controller) {
        financesTableController = controller;
    }

    @Override
    public void registerControllerOpenItemsController(OpenItemsViewController controller) {
        openItemsViewController = controller;
    }

    @Override
    public void registerControllerDailyView(DailyViewController controller) {
        dailyViewController = controller;
    }

    @Override
    public void registerControllerFinances(FinancesController controller) {
        financesController = controller;
    }

    @Override
    public void registerControllerMainController(MainController controller) {
        this.mainController = controller;
    }

    @Override
    public void loadUserDataToController(UsersEntity usersEntity) {
        userViewController.loadUserData(usersEntity);
    }

    @Override
    public void loadProjectDataToController(ProjectsEntity projectsEntity) {
        Platform.runLater(() -> projectViewController.loadProjectData(projectsEntity));
        Platform.runLater(() -> hoursTableController.refreshTableData(projectsEntity));
        Platform.runLater(() -> financesTableController.refreshTableData(projectsEntity));
    }

    @Override
    public void refreshDailyTableData(LocalDate currentDate) {
        Platform.runLater(() -> uploadTableController.refreshTableData(currentDate));
        Platform.runLater(() -> balanceTableController.refreshTableData(currentDate));
    }

    @Override
    public void refreshOpenItemsTable() {
        openItemsViewController.initOpenItemsTable();
    }

    @Override
    public LocalDate getCurrentDate() {
        return dailyViewController.getCurrentDate();
    }

    @Override
    public void showBasicView() {
        mainController.showBasicView();
    }

    @Override
    public void showNewDataView() {
        mainController.showNewDataView();
    }

    @Override
    public StackPane getRoot() {
        return mainController.getRoot();
    }

    private ControllerMediator() {
    }

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }

    public void loadAllData() {
        userViewController.loadUsers();
        projectViewController.loadProjects();
        dailyViewController.updateTables();
        openItemsViewController.initOpenItemsTable();
        financesController.initStatusBox();
    }

}
