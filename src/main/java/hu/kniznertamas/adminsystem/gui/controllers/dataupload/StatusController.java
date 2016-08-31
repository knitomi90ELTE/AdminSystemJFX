package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class StatusController implements Initializable {

    private final GenericDao<StatusEntity> statusDao;

    @FXML
    private JFXTextField nameField;

    @FXML
    private TableView<StatusEntity> statusTable;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

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
    private void onSaveAction() {
        if(!validForm()){
            LOGGER.info("Hiba a bevitt adatokban!");
            DialogManager.showDialog("Hiba", "Hiba a bevitt adatokban!", "Ok", "error");
            clearFields();
            return;
        }
        StatusEntity newStatus = new StatusEntity();
        newStatus.setName(nameField.getText());
        statusDao.create(newStatus);
        clearFields();
        initStatusTable();
        DialogManager.showDialog("Mentés", "Sikeres mentés!", "Ok", "accept");
        LOGGER.info("Sikeres mentés! {}", newStatus.getName());
    }

    @FXML
    private void onDeleteAction() {
        StatusEntity selected = statusTable.getSelectionModel().getSelectedItem();
        if(selected == null) {
        	return;
        }
        statusDao.delete(statusDao.findById(selected.getId()));
        LOGGER.info("Sikeres törlés! {}", selected.getName());
        initStatusTable();
    }

    private void clearFields(){
        nameField.setText("");
    }

}
