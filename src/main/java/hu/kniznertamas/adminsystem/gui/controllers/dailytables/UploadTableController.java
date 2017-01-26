package hu.kniznertamas.adminsystem.gui.controllers.dailytables;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ExtendedUploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.PopOverElement;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class UploadTableController implements Initializable {

    @FXML
    private TableView<ExtendedUploadEntity> uploadTable;

    private final GenericDao<UploadEntity> uploadDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadTableController.class);

    public UploadTableController() {
        uploadDao = DaoManager.getInstance().getUploadDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerUploadTable(this);
    }

    @FXML
    private void addNewAction() {
        new PopOverElement<NewUploadController>("/view/dailytables/NewUploadView.fxml", null,
                () -> refreshTableData(ControllerMediator.getInstance().getCurrentDate()));
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
        ControllerMediator.getInstance().refreshDailyTableData(eue.getCreated().toLocalDate());
    }

    @FXML
    private void editSelectedAction() {
        ExtendedUploadEntity eue = uploadTable.getSelectionModel().getSelectedItem();
        if (eue == null) {
            return;
        }
        new PopOverElement<NewUploadController>("/view/dailytables/NewUploadView.fxml", eue,
                () -> refreshTableData(ControllerMediator.getInstance().getCurrentDate()));
    }

}
