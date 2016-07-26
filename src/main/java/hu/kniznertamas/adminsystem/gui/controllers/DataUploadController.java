package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class DataUploadController implements Initializable {

    public DataUploadController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void toUserView(){
        Main.getInstance().getChangeContent().replaceSceneContent("/view/dataupload/User.fxml");
    }

    @FXML
    private void toProjectView(){
        Main.getInstance().getChangeContent().replaceSceneContent("/view/dataupload/Project.fxml");
    }

    @FXML
    private void toStatusView(){
        Main.getInstance().getChangeContent().replaceSceneContent("/view/dataupload/Status.fxml");
    }

}
