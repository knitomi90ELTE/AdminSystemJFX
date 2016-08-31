package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedBalanceEntity;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.NewBalanceController;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class OpenItemsViewController implements Initializable {

    @FXML
    private TableView<ExtendedBalanceEntity> openItemsTable;

    private final GenericDao<BalanceEntity> balanceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenItemsViewController.class);

    public OpenItemsViewController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerOpenItemsController(this);
        initOpenItemsTable();
    }

    public void initOpenItemsTable() {
        Stream<BalanceEntity> balanceList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = balanceList.filter(item -> item.getCompleted() == null).collect(Collectors.toList());
        List<ExtendedBalanceEntity> extendedList = EntityHelper.createExtendedBalanceEntityList(filteredList);
        LOGGER.info("Data: {}", extendedList);
        openItemsTable.setItems(FXCollections.observableArrayList(extendedList));
        openItemsTable.refresh();
    }

    @FXML
    private void onPayButtonAction() {
        ExtendedBalanceEntity ebe = openItemsTable.getSelectionModel().getSelectedItem();
        if(ebe == null) {
            return;
        }
        BalanceEntity selectedEntity = balanceDao.findById(ebe.getId());
        new PopOverElement<NewBalanceController>("/view/dailytables/NewBalanceView.fxml", selectedEntity, this::initOpenItemsTable);
    }

    @FXML
    private void onNewButtonAction() {
        new PopOverElement<NewBalanceController>("/view/dailytables/NewBalanceView.fxml", null, this::initOpenItemsTable);
    }
}
