package hu.kniznertamas.adminsystem.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.FinancesController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.OpenItemsViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.ProjectViewController;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.UserViewController;
import hu.kniznertamas.adminsystem.helper.Spinner;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

public class BasicViewController implements Initializable {

    @Autowired
    private UserViewController userViewController;

    @Autowired
    private ProjectViewController projectViewController;

    @Autowired
    private DailyViewController dailyViewController;

    @Autowired
    private OpenItemsViewController openItemsViewController;

    @Autowired
    private FinancesController financesController;

    public BasicViewController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataToBasicView();
    }

    private void loadDataToBasicView() {
        Spinner sp = new Spinner();
        sp.activateProgressBar(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                loadAllData();
                updateProgress(9, 10);
                Thread.sleep(2000);
                updateProgress(10, 10);
                return null;
            }
        });
    }

    private void loadAllData() {
        userViewController.loadUsers();
        projectViewController.loadProjects();
        dailyViewController.updateTables();
        openItemsViewController.initOpenItemsTable();
        financesController.initStatusBox();
    }
}
