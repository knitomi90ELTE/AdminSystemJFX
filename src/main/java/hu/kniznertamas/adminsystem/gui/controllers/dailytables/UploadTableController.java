package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UploadTableController implements Initializable {

    @FXML
    private TableView<ExtendedUploadEntity> uploadTable;

    private final GenericDao<UsersEntity> userDao;
    private final GenericDao<UploadEntity> uploadDao;
    private final GenericDao<ProjectsEntity> projectsDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadTableController.class);

    public UploadTableController() {
        userDao = DaoManager.getInstance().getUserDao();
        uploadDao = DaoManager.getInstance().getUploadDao();
        projectsDao = DaoManager.getInstance().getProjectsDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerUploadTable(this);
    }

    @FXML
    private void addNewAction(){
        new PopOverElement<NewUploadController>("/view/dailytables/NewUploadView.fxml", null, () -> refreshTableData(LocalDate.now()));
    }

    public void refreshTableData(LocalDate currentDate){
        Stream<UploadEntity> uploadList = uploadDao.findAll().stream();
        List<UploadEntity> filteredList = uploadList.filter(item -> item.getCreated().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
        List<ExtendedUploadEntity> extendedList = new ArrayList<>();
        for (UploadEntity ue : filteredList){
            ExtendedUploadEntity eue = new ExtendedUploadEntity(ue);
            eue.setProject_name(projectsDao.findById(ue.getProjectId()).getName());
            eue.setUser_name(userDao.findById(ue.getUserId()).getName());
            extendedList.add(eue);
        }
        LOGGER.info("Data: {}", extendedList);
        uploadTable.setItems(FXCollections.observableArrayList(extendedList));
        uploadTable.refresh();
    }

    @FXML
    private void removeSelectedAction() {
        ExtendedUploadEntity eue = uploadTable.getSelectionModel().getSelectedItem();
        if(eue == null) {
        	return;
        }
        LOGGER.info("Removing entity: {}", eue.getId());
        uploadDao.delete(uploadDao.findById(eue.getId()));
        ControllerMediator.getInstance().refreshDailyTableData(eue.getCreated().toLocalDate());
    }
}
