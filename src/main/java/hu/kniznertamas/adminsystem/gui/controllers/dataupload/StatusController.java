package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

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

    private GenericDao<StatusEntity> statusDao;

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

    @FXML
    private void onSaveAction(ActionEvent event) {
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
