package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class UserController implements Initializable, DataUpload {

	@Autowired
	private GenericDao<UsersEntity> userDao;

	@FXML
	private JFXTextField nameField;

	@FXML
	private NumberTextField wageField;

	@FXML
	private JFXTextField noteField;

	@FXML
	private TableView<UsersEntity> userTable;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	public UserController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDataTable();
	}

	public void initDataTable() {
		userTable.setItems(FXCollections.observableArrayList(userDao.findAll()));
	}

	public boolean validForm() {
		if ("".equals(nameField.getText()))
			return false;
		if ("".equals(wageField.getText()))
			return false;
		try {
			Integer.parseInt(wageField.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@FXML
	public void onSaveAction() {
		if (!Validation.isValid(this)) {
			LOGGER.info("Hiba a bevitt adatokban!");
			return;
		}
		UsersEntity newUser = new UsersEntity();
		newUser.setName(nameField.getText());
		newUser.setWage(Integer.parseInt(wageField.getText()));
		newUser.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
		userDao.create(newUser);
		clearFields();
		initDataTable();
		DialogManager.showDialog("Mentés", "Sikeres mentés!", "Ok", "accept");
		LOGGER.info("Sikeres mentés! {}", newUser.getName());
	}

	@FXML
	public void onDeleteAction() {
		UsersEntity selected = userTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			return;
		}
		userDao.delete(userDao.findById(selected.getId()));
		LOGGER.info("Sikeres törlés! {}", selected.getName());
		initDataTable();
	}

	public void clearFields() {
		nameField.setText("");
		wageField.setText("");
		noteField.setText("");
	}

}
