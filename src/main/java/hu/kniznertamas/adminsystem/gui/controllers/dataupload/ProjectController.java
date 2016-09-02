package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class ProjectController implements Initializable, DataUpload {

    private final GenericDao<ProjectsEntity> projectDao;

    @FXML
    private JFXTextField nameField;

    @FXML
    private NumberTextField retentionField;

    @FXML
    private JFXTextField noteField;

    @FXML
    private TableView<ProjectsEntity> projectTable;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    public ProjectController() {
        projectDao = DaoManager.getInstance().getProjectsDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	initDataTable();
    }

    public void initDataTable() {
        projectTable.setItems(FXCollections.observableArrayList(projectDao.findAll()));
    }

    public boolean validForm() {
        if ("".equals(nameField.getText())) return false;
        if ("".equals(retentionField.getText())) return false;
        try {
            Integer.parseInt(retentionField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    public void onSaveAction() {
        if(!Validation.isValid(this)) {
        	LOGGER.info("Hiba a bevitt adatokban!");
        	return;
        }
        ProjectsEntity newProject = new ProjectsEntity();
        newProject.setName(nameField.getText());
        newProject.setRetention(Integer.parseInt(retentionField.getText()));
        newProject.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
        projectDao.create(newProject);
        clearFields();
        initDataTable();
        DialogManager.showDialog("Mentés", "Sikeres mentés!", "Ok", "accept");
        LOGGER.info("Sikeres mentés! {}", newProject.getName());
    }

    @FXML
    public void onDeleteAction() {
        ProjectsEntity selected = projectTable.getSelectionModel().getSelectedItem();
        if(selected == null) {
        	return;
        }
        projectDao.delete(projectDao.findById(selected.getId()));
        LOGGER.info("Sikeres törlés! {}", selected.getName());
        initDataTable();
    }

    public void clearFields() {
        nameField.setText("");
        retentionField.setText("");
        noteField.setText("");
    }
}
