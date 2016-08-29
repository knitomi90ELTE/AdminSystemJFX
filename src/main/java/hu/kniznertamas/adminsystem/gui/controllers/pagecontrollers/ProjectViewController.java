package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProjectViewController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label retentionLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private JFXComboBox<ProjectsEntity> comboBox;

    public ProjectViewController() {
        loadProjects();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerProjects(this);
    }

    public void loadProjectData(ProjectsEntity projectsEntity) {
        nameLabel.setText("Név: " + projectsEntity.getName());
        retentionLabel.setText("Garanciális visszatartás: " + projectsEntity.getRetention().toString() + " Ft");
        noteLabel.setText("Megjegyzés: " + projectsEntity.getNote());
    }

    private void loadProjects() {
        Platform.runLater(() -> {
			GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
		    List<ProjectsEntity> allProjects = projectsDao.findAll();
		    comboBox.setItems(FXCollections.observableArrayList(allProjects));
		    EntityHelper.initComboBoxWithProjectsEntity(comboBox);
		    comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ControllerMediator.getInstance().loadProjectDataToController(newValue));
		});
    }


}
