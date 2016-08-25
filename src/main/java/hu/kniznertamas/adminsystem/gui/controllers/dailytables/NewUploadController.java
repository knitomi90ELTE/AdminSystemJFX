package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.elements.PopupAbstractt;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewUploadController extends PopupAbstractt implements Initializable {

    @FXML
    private JFXComboBox<UsersEntity> userBox;

    @FXML
    private JFXComboBox<ProjectsEntity> projectBox;

    @FXML
    private TextField hoursField;

    @FXML
    private JFXDatePicker createdPicker;

    @FXML
    private TextField noteField;

    private PopOver parent;
    private CallbackInterface callbackFunction;

    private static final Logger LOGGER = LoggerFactory.getLogger(NewUploadController.class);

    public NewUploadController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDate();
        loadUsers();
        loadProjects();
    }

    private boolean validForm() {
        if ("".equals(hoursField.getText())) return false;
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(hoursField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    private void onSaveAction() {
        if (!validForm()) {
            /*Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Hiba a bevitt adatokban!");
            errorAlert.showAndWait();*/
            hoursField.setText("HIBÁS ÉRTÉK");
            return;
        }
        UploadEntity newUpload = new UploadEntity();
        newUpload.setUserId((userBox.getSelectionModel().getSelectedItem()).getId());
        newUpload.setProjectId((projectBox.getSelectionModel().getSelectedItem()).getId());
        newUpload.setHour(Double.parseDouble(hoursField.getText()));
        newUpload.setCreated(Date.valueOf(createdPicker.getValue()));
        newUpload.setNote((noteField.getText().length() == 0) ? "" : noteField.getText());
        GenericDao<UploadEntity> uploadDao = DaoManager.getInstance().getUploadDao();
        uploadDao.create(newUpload);
        LOGGER.info("Saved entity: {}", newUpload);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void initDate() {
        LocalDate today = LocalDate.now();
        createdPicker.setValue(today);
    }

    private void loadUsers() {
        new Thread() {
            @Override
            public void run() {
                GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
                List<UsersEntity> allUsers = userDao.findAll();
                userBox.setItems(FXCollections.observableArrayList(allUsers));
                /*userBox.setCellFactory(new Callback<ListView<UsersEntity>, ListCell<UsersEntity>>() {
                    @Override
                    public ListCell<UsersEntity> call(ListView<UsersEntity> param) {
                        return new ListCell<UsersEntity>() {
                            @Override
                            public void updateItem(UsersEntity item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.getName());
                                    setGraphic(null);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });*/
                EntityHelper.initComboBoxWithUserEntity(userBox);
                userBox.getSelectionModel().selectFirst();
            }
        }.start();
    }

    private void loadProjects() {
        new Thread() {
            @Override
            public void run() {
                GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
                List<ProjectsEntity> allProjects = projectsDao.findAll();
                projectBox.setItems(FXCollections.observableArrayList(allProjects));
                /*projectBox.setCellFactory(new Callback<ListView<ProjectsEntity>, ListCell<ProjectsEntity>>() {
                    @Override
                    public ListCell<ProjectsEntity> call(ListView<ProjectsEntity> param) {
                        return new ListCell<ProjectsEntity>() {
                            @Override
                            public void updateItem(ProjectsEntity item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.getName());
                                    setGraphic(null);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });*/
                EntityHelper.initComboBoxWithProjectsEntity(projectBox);
                projectBox.getSelectionModel().selectFirst();
            }
        }.start();
    }

    @Override
    public void setParent(PopOver parent) {
        this.parent = parent;
    }

    @Override
    public void loadEntityToFields(PersistentEntity entity) {

    }

    @Override
    public void setCallbackFunction(CallbackInterface callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    @Override
    protected void onCancelAction() {
        callbackFunction.callbackFunction();
        parent.hide();
    }
}
