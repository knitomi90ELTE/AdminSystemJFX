package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.Main;
import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.ChangeContent;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.io.IOException;
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

    private GenericDao<UsersEntity> userDao;
    private GenericDao<UploadEntity> uploadDao;
    private GenericDao<ProjectsEntity> projectsDao;

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
    private void addNewAction(ActionEvent event){
        PopOver popover = new PopOver();
        popover.setAutoHide(false);
        FXMLLoader loader = Main.getInstance().getChangeContent().getContentNode("/view/dailytables/NewUploadView.fxml");
        try {
            popover.setContentNode(loader.load());
            NewUploadController controller = loader.getController();
            controller.setParent(popover);
        } catch (IOException e) {
            e.printStackTrace();
        }
        popover.show(Main.getInstance().getChangeContent().getMainStage());
    }

    public void refreshTableData(LocalDate currentDate){
        Stream<UploadEntity> uploadList = uploadDao.findAll().stream();
        List<UploadEntity> filteredList = uploadList.filter(item -> item.getCreated().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
        System.out.println(filteredList.toString());
        List<ExtendedUploadEntity> extendedList = new ArrayList<>();
        for (UploadEntity ue : filteredList){
            ExtendedUploadEntity eue = new ExtendedUploadEntity(ue);
            eue.setProject_name(projectsDao.findById(ue.getProjectId()).getName());
            eue.setUser_name(userDao.findById(ue.getUserId()).getName());
            extendedList.add(eue);
        }
        uploadTable.setItems(FXCollections.observableArrayList(extendedList));
        uploadTable.refresh();
    }
}
