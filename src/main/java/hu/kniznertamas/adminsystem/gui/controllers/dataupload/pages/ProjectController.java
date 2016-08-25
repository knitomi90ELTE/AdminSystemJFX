package hu.kniznertamas.adminsystem.gui.controllers.dataupload.pages;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    private final GenericDao<ProjectsEntity> projectDao;

    @FXML
    private TextField nameField;

    @FXML
    private TextField retentionField;

    @FXML
    private TextField noteField;

    @FXML
    private TableView<ProjectsEntity> projectTable;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    public ProjectController() {
        projectDao = DaoManager.getInstance().getProjectsDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProjectsTable();
    }

    private void initProjectsTable() {
        projectTable.setItems(FXCollections.observableArrayList(projectDao.findAll()));
    }

    private boolean validForm() {
        if ("".equals(nameField.getText())) return false;
        if ("".equals(retentionField.getText())) return false;
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(retentionField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    private void onSaveAction() {
        if (!validForm()) {
            LOGGER.info("Hiba a bevitt adatokban!");
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Hiba a bevitt adatokban!");
            errorAlert.showAndWait();
            clearFields();
            return;
        }
        ProjectsEntity newProject = new ProjectsEntity();
        newProject.setName(nameField.getText());
        newProject.setRetention(Integer.parseInt(retentionField.getText()));
        newProject.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
        projectDao.create(newProject);
        clearFields();
        initProjectsTable();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        LOGGER.info("Sikeres mentés! {}", newProject.getName());
        alert.setHeaderText("Sikeres mentés!");
        alert.showAndWait();
    }

    @FXML
    private void onDeleteAction() {
        ProjectsEntity selected = projectTable.getSelectionModel().getSelectedItem();
        if(selected == null) {
        	return;
        }
        projectDao.delete(projectDao.findById(selected.getId()));
        LOGGER.info("Sikeres törlés! {}", selected.getName());
        initProjectsTable();
    }

    private void clearFields() {
        nameField.setText("");
        retentionField.setText("");
        noteField.setText("");
    }
}
