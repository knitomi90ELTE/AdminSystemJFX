package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.FinancesTableController;
import hu.kniznertamas.adminsystem.gui.controllers.projecttables.HoursTableController;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjectViewController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label retentionLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private JFXComboBox<ProjectsEntity> comboBox;

    @Autowired
    private GenericDao<ProjectsEntity> projectsDao;

    @Autowired
    private ProjectViewController projectViewController;

    @Autowired
    private HoursTableController hoursTableController;

    @Autowired
    private FinancesTableController financesTableController;

    public void loadProjectData(ProjectsEntity projectsEntity) {
        nameLabel.setText("Név: " + projectsEntity.getName());
        retentionLabel.setText("Garanciális visszatartás: " + projectsEntity.getRetention().toString() + " Ft");
        noteLabel.setText("Megjegyzés: " + projectsEntity.getNote());
    }

    public void loadProjects() {
        List<ProjectsEntity> allProjects = projectsDao.findAll();
        comboBox.setItems(FXCollections.observableArrayList(allProjects));
        EntityHelper.initComboBoxWithEntity(comboBox);
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            projectViewController.loadProjectData(newValue);
            hoursTableController.refreshTableData(newValue);
            financesTableController.refreshTableData(newValue);
        });
    }

}
