package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BalanceTableController implements Initializable {

    @FXML
    private TableView<ExtendedBalanceEntity> balanceTable;


    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceTableController.class);
    private final GenericDao<BalanceEntity> balanceDao;
    public BalanceTableController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerBalanceTable(this);
    }

    public void refreshTableData(LocalDate currentDate){
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> (item.getCompleted() != null) && item.getCompleted().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = EntityHelper.createExtendedBalanceEntityList(filteredList);
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
