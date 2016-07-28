package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

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

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private GenericDao<UsersEntity> userDao;

    @FXML
    private TextField nameField;

    @FXML
    private TextField wageField;

    @FXML
    private TextField noteField;

    @FXML
    private TableView<UsersEntity> userTable;

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
            Integer.parseInt(wageField.getText());
        } catch (NumberFormatException e){
            return false;
        }
        return true;
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
        UsersEntity newUser = new UsersEntity();
        newUser.setName(nameField.getText());
        newUser.setWage(Integer.parseInt(wageField.getText()));
        newUser.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
        userDao.create(newUser);
        clearFields();
        initUserTable();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sikeres mentés!");
        alert.showAndWait();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
        UsersEntity selected = userTable.getSelectionModel().getSelectedItem();
        userDao.delete(userDao.findById(selected.getId()));
        initUserTable();
    }

    private void clearFields(){
        nameField.setText("");
        wageField.setText("");
        noteField.setText("");
    }

}
