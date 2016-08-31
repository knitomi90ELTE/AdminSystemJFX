package hu.kniznertamas.adminsystem.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenuBarController implements Initializable {

    public MenuBarController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addNewData() {
        ControllerMediator.getInstance().showNewDataView();
    }

    @FXML
    private void showMain() {
    	ControllerMediator.getInstance().showBasicView();
    }

}
