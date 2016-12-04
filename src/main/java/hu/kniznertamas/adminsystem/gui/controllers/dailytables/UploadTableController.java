package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ExtendedUploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers.DailyViewController;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class UploadTableController {

    @FXML
    private TableView<ExtendedUploadEntity> uploadTable;

    @Autowired
    private GenericDao<UploadEntity> uploadDao;

    @Autowired
    private DailyViewController dailyViewController;

    @Autowired
    private UploadTableController uploadTableController;

    @Autowired
    private BalanceTableController balanceTableController;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadTableController.class);

    @FXML
    private void addNewAction() {
        new PopOverElement<NewUploadController>("/view/dailytables/NewUploadView.fxml", null,
                () -> refreshTableData(dailyViewController.getCurrentDate()));
    }

    public void refreshTableData(LocalDate currentDate) {
        Stream<UploadEntity> uploadList = uploadDao.findAll().stream();
        List<UploadEntity> filteredList = uploadList.filter(item -> item.getCreated().equals(Date.valueOf(currentDate)))
                .collect(Collectors.toList());
        List<ExtendedUploadEntity> extendedList = EntityHelper.createExtendedUploadEntityList(filteredList);
        LOGGER.info("Data: {}", extendedList);
        uploadTable.setItems(FXCollections.observableArrayList(extendedList));
        uploadTable.refresh();
    }

    @FXML
    private void removeSelectedAction() {
        ExtendedUploadEntity eue = uploadTable.getSelectionModel().getSelectedItem();
        if (eue == null) {
            return;
        }
        LOGGER.info("Removing entity: {}", eue.getId());
        uploadDao.delete(uploadDao.findById(eue.getId()));
        refreshDailyTableData(eue.getCreated().toLocalDate());
    }

    public void refreshDailyTableData(LocalDate currentDate) {
        uploadTableController.refreshTableData(currentDate);
        balanceTableController.refreshTableData(currentDate);
    }
}
