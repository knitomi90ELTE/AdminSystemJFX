package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

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
            Integer.parseInt(retentionField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    private void onSaveAction(ActionEvent event) {
        if (!validForm()) {
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
        alert.setHeaderText("Sikeres mentés!");
        alert.showAndWait();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
        ProjectsEntity selected = projectTable.getSelectionModel().getSelectedItem();
        projectDao.delete(projectDao.findById(selected.getId()));
        initProjectsTable();
    }

    private void clearFields() {
        nameField.setText("");
        retentionField.setText("");
        noteField.setText("");
    }
}
