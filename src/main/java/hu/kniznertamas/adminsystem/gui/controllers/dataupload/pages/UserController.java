package hu.kniznertamas.adminsystem.gui.controllers.dataupload.pages;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private final GenericDao<UsersEntity> userDao;

    @FXML
    private TextField nameField;

    @FXML
    private TextField wageField;

    @FXML
    private TextField noteField;

    @FXML
    private TableView<UsersEntity> userTable;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        userDao = DaoManager.getInstance().getUserDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUserTable();
    }

    private void initUserTable() {
        userTable.setItems(FXCollections.observableArrayList(userDao.findAll()));
    }

    private boolean validForm() {
        if("".equals(nameField.getText())) return false;
        if("".equals(wageField.getText())) return false;
        try{
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(wageField.getText());
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    @FXML
    private void onSaveAction() {
        if(!validForm()){
            LOGGER.info("Hiba a bevitt adatokban!");
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Hiba a bevitt adatokban!");
            errorAlert.showAndWait();
            clearFields();
            return;
        }
        UsersEntity newUser = new UsersEntity();
        newUser.setName(nameField.getText());
        newUser.setWage(Integer.parseInt(wageField.getText()));
        newUser.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
        userDao.create(newUser);
        clearFields();
        initUserTable();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        LOGGER.info("Sikeres mentés! {}", newUser.getName());
        alert.setHeaderText("Sikeres mentés!");
        alert.showAndWait();
    }

    @FXML
    private void onDeleteAction() {
        UsersEntity selected = userTable.getSelectionModel().getSelectedItem();
        if(selected == null) {
        	return;
        }
        userDao.delete(userDao.findById(selected.getId()));
        LOGGER.info("Sikeres törlés! {}", selected.getName());
        initUserTable();
    }

    private void clearFields(){
        nameField.setText("");
        wageField.setText("");
        noteField.setText("");
    }

}
