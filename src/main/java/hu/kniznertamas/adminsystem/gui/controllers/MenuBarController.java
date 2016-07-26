package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    public MenuBarController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addNewData(ActionEvent event) {
        Main.getInstance().getChangeContent().replaceSceneContent("/view/DataUpload.fxml");
    }

    @FXML
    private void showMain(ActionEvent event) {
        Main.getInstance().getChangeContent().replaceSceneContent("/view/Main.fxml");
    }

}
