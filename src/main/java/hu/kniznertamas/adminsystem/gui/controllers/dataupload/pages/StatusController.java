package hu.kniznertamas.adminsystem.gui.controllers.dataupload.pages;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable {

    private final GenericDao<StatusEntity> statusDao;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<StatusEntity> statusTable;

    public StatusController() {
        statusDao = DaoManager.getInstance().getStatusDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initStatusTable();
    }

    private void initStatusTable(){
        statusTable.setItems(FXCollections.observableArrayList(statusDao.findAll()));
    }

    private boolean validForm() {
        return !"".equals(nameField.getText());
    }

    @FXML
    private void onSaveAction(ActionEvent event) {
        if(!validForm()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Hiba a bevitt adatokban!");
            errorAlert.showAndWait();
            clearFields();
            return;
        }
        StatusEntity newStatus = new StatusEntity();
        newStatus.setName(nameField.getText());
        statusDao.create(newStatus);
        clearFields();
        initStatusTable();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sikeres mentés!");
        alert.showAndWait();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
        StatusEntity selected = statusTable.getSelectionModel().getSelectedItem();
        statusDao.delete(statusDao.findById(selected.getId()));
        initStatusTable();
    }

    private void clearFields(){
        nameField.setText("");
    }

}
