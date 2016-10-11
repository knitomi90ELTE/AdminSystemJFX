package hu.kniznertamas.adminsystem.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.Spinner;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

public class BasicViewController implements Initializable {

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
                ControllerMediator.getInstance().loadAllData();
                updateProgress(9, 10);
                Thread.sleep(2000);
                updateProgress(10, 10);
                return null;
            }
        });
    }
}
