package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.Main;
import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.NewBalanceController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
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

public class OpenItemsViewController implements Initializable {

    @FXML
    private TableView<ExtendedBalanceEntity> openItemsTable;

    private final GenericDao<BalanceEntity> balanceDao;
    private final GenericDao<StatusEntity> statusDao;
    private final GenericDao<UsersEntity> userDao;
    private final GenericDao<ProjectsEntity> projectsDao;

    public OpenItemsViewController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
        statusDao = DaoManager.getInstance().getStatusDao();
        userDao = DaoManager.getInstance().getUserDao();
        projectsDao = DaoManager.getInstance().getProjectsDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initOpenItemsTable();
    }

    private void initOpenItemsTable() {
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> item.getCompleted() == null).collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = new ArrayList<>();
        for (BalanceEntity be : filteredList){
            ExtendedBalanceEntity ebe = new ExtendedBalanceEntity(be);
            ebe.setStatus_name(statusDao.findById(be.getStatusId()).getName());
            if("project".equals(ebe.getModelName())){
                ProjectsEntity pe = projectsDao.findById(be.getModelId());
                ebe.setModel(pe);
                ebe.setDisplay_name(pe.getName());
            } else if("user".equals(ebe.getModelName())){
                UsersEntity ue = userDao.findById(be.getModelId());
                ebe.setModel(ue);
                ebe.setDisplay_name(ue.getName());
            } else {
                ebe.setModel(null);
                ebe.setDisplay_name("");
            }
            extendedList.add(ebe);
        }
        openItemsTable.setItems(FXCollections.observableArrayList(extendedList));
        openItemsTable.refresh();
    }

    @FXML
    private void onPayButtonAction() {
        ExtendedBalanceEntity ebe = openItemsTable.getSelectionModel().getSelectedItem();
        BalanceEntity selectedEntity = balanceDao.findById(ebe.getId());
        selectedEntity.setCompleted(Date.valueOf(LocalDate.now()));
        balanceDao.update(selectedEntity);
        initOpenItemsTable();
    }

    @FXML
    private void onNewButtonAction() {
        PopOver popover = new PopOver();
        popover.setAutoHide(false);
        FXMLLoader loader = Main.getInstance().getChangeContent().getContentNode("/view/dailytables/NewBalanceView.fxml");
        try {
            popover.setContentNode(loader.load());
            NewBalanceController controller = loader.getController();
            controller.setParent(popover);
        } catch (IOException e) {
            e.printStackTrace();
        }
        popover.show(Main.getInstance().getChangeContent().getMainStage());
    }
}
