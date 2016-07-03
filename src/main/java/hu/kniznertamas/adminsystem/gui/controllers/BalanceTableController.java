package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BalanceTableController implements Initializable {

    @FXML
    private TableView<ExtendedBalanceEntity> balanceTable;

    private GenericDao<BalanceEntity> balanceDao;
    private GenericDao<StatusEntity> statusDao;
    private GenericDao<UsersEntity> userDao;
    private GenericDao<ProjectsEntity> projectsDao;

    public BalanceTableController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
        statusDao = DaoManager.getInstance().getStatusDao();
        userDao = DaoManager.getInstance().getUserDao();
        projectsDao = DaoManager.getInstance().getProjectsDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerBalanceTable(this);
    }

    public void refreshTableData(LocalDate currentDate){
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> item.getCreated().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
        System.out.println(filteredList.toString());
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
        balanceTable.setItems(FXCollections.observableArrayList(extendedList));
        balanceTable.refresh();
    }
}
