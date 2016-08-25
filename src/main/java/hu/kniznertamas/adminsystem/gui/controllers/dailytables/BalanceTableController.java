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

public class BalanceTableController implements Initializable {

    @FXML
    private TableView<ExtendedBalanceEntity> balanceTable;


    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceTableController.class);
    private final GenericDao<BalanceEntity> balanceDao;
    private final GenericDao<StatusEntity> statusDao;
    private final GenericDao<UsersEntity> userDao;
    private final GenericDao<ProjectsEntity> projectsDao;

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
        List<BalanceEntity> filteredList = balanceList.filter(item -> (item.getCompleted() != null) && item.getCompleted().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
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
        LOGGER.info("Data: {}", extendedList);
        balanceTable.setItems(FXCollections.observableArrayList(extendedList));
        balanceTable.refresh();
    }

    @FXML
    private void addNewAction(){
        new PopOverElement<NewBalanceController>("/view/dailytables/NewBalanceView.fxml", null, () -> refreshTableData(LocalDate.now()));
    }

    @FXML
    private void removeSelectedAction() {
        ExtendedBalanceEntity ebe = balanceTable.getSelectionModel().getSelectedItem();
        if(ebe == null) {
        	return;
        }
        LOGGER.info("Removing entity: {}", ebe.getId());
        balanceDao.delete(balanceDao.findById(ebe.getId()));
        ControllerMediator.getInstance().refreshDailyTableData(ebe.getCreated().toLocalDate());
    }

}
