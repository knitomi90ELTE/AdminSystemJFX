package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class StatusController implements Initializable, DataUpload {

	@Autowired
	private GenericDao<StatusEntity> statusDao;

	@FXML
	private JFXTextField nameField;

	@FXML
	private TableView<StatusEntity> statusTable;

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

	public StatusController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDataTable();
	}

	public void initDataTable() {
		statusTable.setItems(FXCollections.observableArrayList(statusDao.findAll()));
	}

	public boolean validForm() {
		return !"".equals(nameField.getText());
	}

	@FXML
	public void onSaveAction() {
		if (!Validation.isValid(this)) {
			LOGGER.info("Hiba a bevitt adatokban!");
			return;
		}
		StatusEntity newStatus = new StatusEntity();
		newStatus.setName(nameField.getText());
		statusDao.create(newStatus);
		clearFields();
		initDataTable();
		DialogManager.showDialog("Mentés", "Sikeres mentés!", "Ok", "accept");
		LOGGER.info("Sikeres mentés! {}", newStatus.getName());
	}

	@FXML
	public void onDeleteAction() {
		StatusEntity selected = statusTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			return;
		}
		statusDao.delete(statusDao.findById(selected.getId()));
		LOGGER.info("Sikeres törlés! {}", selected.getName());
		initDataTable();
	}

	public void clearFields() {
		nameField.setText("");
	}

}
