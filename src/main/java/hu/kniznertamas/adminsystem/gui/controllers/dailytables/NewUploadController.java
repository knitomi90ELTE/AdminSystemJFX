package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.elements.NumberTextField;
import hu.kniznertamas.adminsystem.gui.elements.PopupAbstractt;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NewUploadController extends PopupAbstractt implements Initializable {

    @Autowired
    private GenericDao<UsersEntity> usersDao;

    @Autowired
    private GenericDao<ProjectsEntity> projectsDao;

    @Autowired
    private GenericDao<UploadEntity> uploadDao;

    @Autowired
    private DailyViewController dailyViewController;

    @Autowired
    private BalanceTableController balanceTableController;

    @Autowired
    private UploadTableController uploadTableController;

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
        UploadEntity newUpload = new UploadEntity();
        newUpload.setUserId(userBox.getSelectionModel().getSelectedItem().getId());
        newUpload.setProjectId(projectBox.getSelectionModel().getSelectedItem().getId());
        newUpload.setHour(Double.parseDouble(hoursField.getText()));
        newUpload.setCreated(Date.valueOf(createdPicker.getValue()));
        newUpload.setNote(noteField.getText().length() == 0 ? "" : noteField.getText());
        uploadDao.create(newUpload);
        LOGGER.info("Saved entity: {}", newUpload);
        refreshDailyTableData(createdPicker.getValue());
        onCancelAction();
    }

    public void refreshDailyTableData(LocalDate currentDate) {
        uploadTableController.refreshTableData(currentDate);
        balanceTableController.refreshTableData(currentDate);
    }

    private void initDate() {
        createdPicker.setValue(dailyViewController.getCurrentDate());
    }

    private void loadUsers() {
        List<UsersEntity> allUsers = usersDao.findAll();
        userBox.setItems(FXCollections.observableArrayList(allUsers));
        EntityHelper.initComboBoxWithEntity(userBox);
        userBox.getSelectionModel().selectFirst();
    }

    private void loadProjects() {
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
