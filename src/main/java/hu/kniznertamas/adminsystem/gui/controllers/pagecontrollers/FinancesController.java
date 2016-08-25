package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.*;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FinancesController implements Initializable {

    @FXML
    private ComboBox<StatusEntity> statusBox;

    @FXML
    private TableView<ExtendedBalanceEntity> balanceTable;

    private final GenericDao<BalanceEntity> balanceDao;
    private final GenericDao<StatusEntity> statusDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancesController.class);

    public FinancesController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
        statusDao = DaoManager.getInstance().getStatusDao();
    }

    private void initStatusBox() {
        List<StatusEntity> statusList = statusDao.findAll();
        statusBox.setItems(FXCollections.observableArrayList(statusList));
        statusBox.getSelectionModel().selectFirst();
        statusBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadDataToTable());

    }

    private void loadDataToTable(){
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> item.getStatusId().equals(statusBox.getSelectionModel().getSelectedItem().getId())).collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = EntityHelper.createExtendedBalanceEntityList(filteredList);
        LOGGER.info("Data: {}", extendedList);
        balanceTable.setItems(FXCollections.observableArrayList(extendedList));
        balanceTable.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initStatusBox();
        loadDataToTable();
    }
}
