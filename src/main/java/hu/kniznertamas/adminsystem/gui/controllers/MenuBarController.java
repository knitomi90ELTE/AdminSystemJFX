package hu.kniznertamas.adminsystem.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenuBarController implements Initializable {

    @Autowired
    private MainController mainController;

    public MenuBarController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addNewData() {
        mainController.showNewDataView();
    }

    @FXML
    private void showMain() {
        mainController.showBasicView();
    }

}
