package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label retentionLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private Label sumHoursLabel;

    public ProjectViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControlerProjects(this);
    }

    public void loadProjectData(ProjectsEntity projectsEntity){
        nameLabel.setText("Név: " + projectsEntity.getName());
        retentionLabel.setText("Garanciális visszatartás: " + projectsEntity.getRetention().toString() + " Ft");
        noteLabel.setText("Megjegyzés: " + projectsEntity.getNote());
        //sumHoursLabel.setText(Double.toString(hoursTableController.refreshTableData(projectsEntity)) + " óra");
    }


}
