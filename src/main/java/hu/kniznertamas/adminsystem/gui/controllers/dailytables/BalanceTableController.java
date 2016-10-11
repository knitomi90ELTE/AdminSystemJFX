package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedBalanceEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

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

    private void initCellFactory() {
    	balanceTable.setRowFactory(row -> new TableRow<ExtendedBalanceEntity>(){
    	    @Override
    	    public void updateItem(ExtendedBalanceEntity item, boolean empty){
    	    	if(empty) return;
    	        List<Integer> incomeIndexes = Arrays.asList(3, 17, 18);
    	        if(incomeIndexes.contains(item.getStatusId())) {
    	        	setStyle("-fx-background-color: rgba(0, 255, 0, 0.3)");
    	        }
    	    }
    	});
	}

	public void refreshTableData(LocalDate currentDate){
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> (item.getCompleted() != null) && item.getCompleted().equals(Date.valueOf(currentDate))).collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = EntityHelper.createExtendedBalanceEntityList(filteredList);
        LOGGER.info("Data: {}", extendedList);
        balanceTable.setItems(FXCollections.observableArrayList(extendedList));
        initCellFactory();
        balanceTable.refresh();
    }

    @FXML
    private void addNewAction(){
        new PopOverElement<NewBalanceController>("/view/dailytables/NewBalanceView.fxml", null, () -> {
            refreshTableData(ControllerMediator.getInstance().getCurrentDate());
            ControllerMediator.getInstance().refreshOpenItemsTable();
        });
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
