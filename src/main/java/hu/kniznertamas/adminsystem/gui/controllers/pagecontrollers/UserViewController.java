package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ExtendedUploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class UserViewController implements Initializable {

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	@FXML
	private Label noteLabel;

	@FXML
	private TableView<ExtendedUploadEntity> userTable;

	@FXML
	private JFXComboBox<UsersEntity> comboBox;

	public UserViewController() {
		// loadUsers();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ControllerMediator.getInstance().registerControllerUser(this);
	}

	public void loadUserData(UsersEntity usersEntity) {
		nameLabel.setText("Név: " + usersEntity.getName());
		wageLabel.setText("Órabér: " + usersEntity.getWage().toString());
		noteLabel.setText("Megjegyzés: " + usersEntity.getNote());
		GenericDao<UploadEntity> uploadDao = DaoManager.getInstance().getUploadDao();
		Stream<UploadEntity> uploadList = uploadDao.findAll().stream();
		List<UploadEntity> filteredList = uploadList.filter(item -> item.getUserId().equals(usersEntity.getId())).collect(Collectors.toList());
		List<ExtendedUploadEntity> extendedList = EntityHelper.createExtendedUploadEntityList(filteredList);
		userTable.setItems(FXCollections.observableArrayList(extendedList));
		userTable.refresh();
	}

	public void loadUsers() {
		GenericDao<UsersEntity> usersDao = DaoManager.getInstance().getUserDao();
		List<UsersEntity> allUsers = usersDao.findAll();
		comboBox.setItems(FXCollections.observableArrayList(allUsers));
		comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ControllerMediator.getInstance().loadUserDataToController(newValue));
	}

}
