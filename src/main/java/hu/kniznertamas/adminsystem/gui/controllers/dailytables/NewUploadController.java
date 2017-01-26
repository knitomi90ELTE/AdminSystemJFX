package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.gui.elements.PopupAbstractt;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NewUploadController extends PopupAbstractt implements Initializable {

    @FXML
    private JFXComboBox<UsersEntity> userBox;

    @FXML
    private JFXComboBox<ProjectsEntity> projectBox;

    @FXML
    private NumberTextField hoursField;

    @FXML
    private JFXDatePicker createdPicker;

    @FXML
    private JFXTextField noteField;

    private PopOver parent;
    private CallbackInterface callbackFunction;
    private UploadEntity currentEntity = null;

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
        if ("".equals(hoursField.getText())) {
            return false;
        }
        try {
            Double.parseDouble(hoursField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    private void onSaveAction() {
        if (!validForm()) {
            hoursField.setText("HIBÁS ÉRTÉK");
            return;
        }

        GenericDao<UploadEntity> uploadDao = DaoManager.getInstance().getUploadDao();
        if (currentEntity != null) {
            UploadEntity editedUpload = uploadDao.findById(currentEntity.getId());
            LOGGER.info("EDIT");
            editedUpload.setUserId(userBox.getSelectionModel().getSelectedItem().getId());
            editedUpload.setProjectId(projectBox.getSelectionModel().getSelectedItem().getId());
            editedUpload.setHour(Double.parseDouble(hoursField.getText()));
            editedUpload.setCreated(Date.valueOf(createdPicker.getValue()));
            editedUpload.setNote(noteField.getText().length() == 0 ? "" : noteField.getText());
            uploadDao.update(editedUpload);
            LOGGER.info("Edited entity: {}", editedUpload);

        } else {
            UploadEntity newUpload = new UploadEntity();
            newUpload.setUserId(userBox.getSelectionModel().getSelectedItem().getId());
            newUpload.setProjectId(projectBox.getSelectionModel().getSelectedItem().getId());
            newUpload.setHour(Double.parseDouble(hoursField.getText()));
            newUpload.setCreated(Date.valueOf(createdPicker.getValue()));
            newUpload.setNote(noteField.getText().length() == 0 ? "" : noteField.getText());
            LOGGER.info("CREATE");
            uploadDao.create(newUpload);
            LOGGER.info("Saved entity: {}", newUpload);
        }
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    private void initDate() {
        createdPicker.setValue(ControllerMediator.getInstance().getCurrentDate());
    }

    private void loadUsers() {
        GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
        List<UsersEntity> allUsers = userDao.findAll();
        userBox.setItems(FXCollections.observableArrayList(allUsers));
        EntityHelper.initComboBoxWithEntity(userBox);
        userBox.getSelectionModel().selectFirst();
    }

    private void loadProjects() {
        GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
        List<ProjectsEntity> allProjects = projectsDao.findAll();
        projectBox.setItems(FXCollections.observableArrayList(allProjects));
        EntityHelper.initComboBoxWithEntity(projectBox);
        projectBox.getSelectionModel().selectFirst();
    }

    @Override
    public void setParent(PopOver parent) {
        this.parent = parent;
    }

    @Override
    public void loadEntityToFields(PersistentEntity entity) {
        if (entity == null) {
            return;
        }
        UploadEntity uploadEntity = (UploadEntity) entity;
        currentEntity = uploadEntity;
        GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
        userBox.getSelectionModel().select(userDao.findById(uploadEntity.getUserId()));
        GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
        projectBox.getSelectionModel().select(projectsDao.findById(uploadEntity.getProjectId()));
        hoursField.setText(uploadEntity.getHour().toString());
        createdPicker.setValue(uploadEntity.getCreated().toLocalDate());
        noteField.setText(uploadEntity.getNote());
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
