package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedBalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class FinancesController {

    @FXML
    private JFXComboBox<StatusEntity> statusBox;

    @FXML
    private TableView<ExtendedBalanceEntity> balanceTable;

    @Autowired
    private GenericDao<BalanceEntity> balanceDao;

    @Autowired
    private GenericDao<StatusEntity> statusDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancesController.class);

    public void initStatusBox() {
        List<StatusEntity> statusList = statusDao.findAll();
        statusBox.setItems(FXCollections.observableArrayList(statusList));
        statusBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> loadDataToTable());
    }

    private void loadDataToTable() {
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList
                .filter(item -> item.getStatusId().equals(statusBox.getSelectionModel().getSelectedItem().getId()))
                .collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = EntityHelper.createExtendedBalanceEntityList(filteredList);
        LOGGER.info("Data: {}", extendedList);
        balanceTable.setItems(FXCollections.observableArrayList(extendedList));
        balanceTable.refresh();
    }

}
