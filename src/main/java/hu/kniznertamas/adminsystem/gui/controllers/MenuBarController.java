package hu.kniznertamas.adminsystem.gui.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.fxml.FXML;

public class MenuBarController {

    @Autowired
    private MainController mainController;

    @FXML
    private void addNewData() {
        mainController.showNewDataView();
    }

    @FXML
    private void showMain() {
        mainController.showBasicView();
    }

}
