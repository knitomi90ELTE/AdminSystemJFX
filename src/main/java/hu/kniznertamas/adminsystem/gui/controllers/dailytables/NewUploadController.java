package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewUploadController implements Initializable {

    @FXML
    private ComboBox userBox;

    @FXML
    private ComboBox projectBox;

    @FXML
    private TextField hoursField;

    @FXML
    private DatePicker createdPicker;

    @FXML
    private TextField noteField;

    private PopOver parent;

    public NewUploadController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDate();
        loadUsers();
        loadProjects();
    }

    @FXML
    private void onSaveAction(ActionEvent event) {
        UploadEntity newUpload = new UploadEntity();
        newUpload.setUserId(((UsersEntity)userBox.getSelectionModel().getSelectedItem()).getId());
        newUpload.setProjectId(((ProjectsEntity)projectBox.getSelectionModel().getSelectedItem()).getId());
        newUpload.setHour(Double.parseDouble(hoursField.getText()));
        newUpload.setCreated(Date.valueOf(createdPicker.getValue()));
        newUpload.setNote(noteField.getText());
        GenericDao<UploadEntity> uploadDao = DaoManager.getInstance().getUploadDao();
        uploadDao.create(newUpload);
        ControllerMediator.getInstance().refreshDailyTableData(createdPicker.getValue());
        onCancelAction(null);
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        parent.hide();
    }

    private void initDate(){
        LocalDate today = LocalDate.now();
        createdPicker.setValue(today);
    }

    private void loadUsers(){
        new Thread() {
            @Override
            public void run() {
                GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
                List<UsersEntity> allUsers = userDao.findAll();
                userBox.setItems(FXCollections.observableArrayList(allUsers));
                userBox.setCellFactory(new Callback<ListView<UsersEntity>, ListCell<UsersEntity>>() {
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
                });
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
                projectBox.setCellFactory(new Callback<ListView<ProjectsEntity>, ListCell<ProjectsEntity>>() {
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
                });
                projectBox.getSelectionModel().selectFirst();
            }
        }.start();
    }

    public void setParent(PopOver parent){
        this.parent = parent;
    }
}
