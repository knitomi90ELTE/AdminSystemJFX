package hu.kniznertamas.adminsystem.gui.controllers.mediator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerMediator implements IMediateControllers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMediator.class);

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
        /*Platform.runLater(() -> userViewController.loadUsers());
        Platform.runLater(() -> projectViewController.loadProjects());
    	Platform.runLater(() -> dailyViewController.updateTables());
    	Platform.runLater(() -> openItemsViewController.initOpenItemsTable());
    	Platform.runLater(() -> financesController.initStatusBox());*/
        ExecutorService service = Executors.newFixedThreadPool(5);
        try {
            service.invokeAll(createTasks());
            //DialogManager.showDialog("Hiba", "Váratlan hiba történt, kérlek indítsd újra az alkalmazást!", "Rendben", "error");
        } catch (InterruptedException e) {
            LOGGER.error("Exception while loading data {}", e.getMessage());
        } finally {
            service.shutdown();
        }

    }

    private List<Callable<Void>> createTasks() {
        List<Callable<Void>> tasks = new ArrayList<>();
        tasks.add(() -> {
            userViewController.loadUsers();
            return null;
        });
        tasks.add(() -> {
            projectViewController.loadProjects();
            return null;
        });
        tasks.add(() -> {
            dailyViewController.updateTables();
            return null;
        });
        tasks.add(() -> {
            openItemsViewController.initOpenItemsTable();
            return null;
        });
        tasks.add(() -> {
            financesController.initStatusBox();
            return null;
        });
        return tasks;
    }

}
