package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfoenix.controls.JFXDatePicker;

import hu.kniznertamas.adminsystem.gui.controllers.dailytables.BalanceTableController;
import hu.kniznertamas.adminsystem.gui.controllers.dailytables.UploadTableController;
import javafx.fxml.FXML;

public class DailyViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyViewController.class);

    @Autowired
    private UploadTableController uploadTableController;

    @Autowired
    private BalanceTableController balanceTableController;

    @FXML
    private JFXDatePicker datePicker;

    private LocalDate currentDate;

    public void init() {
        LocalDate ld = LocalDate.now();
        datePicker.setValue(ld);
        currentDate = ld;
    }

    public void decreaseDate() {
        LocalDate ld = datePicker.getValue();
        ld = ld.minusDays(1);
        datePicker.setValue(ld);
        currentDate = ld;
    }

    public void increaseDate() {
        LocalDate ld = datePicker.getValue();
        ld = ld.plusDays(1);
        datePicker.setValue(ld);
        currentDate = ld;
    }

    public void onDateChangeAction() {
        currentDate = datePicker.getValue();
        updateTables();
    }

    public void updateTables() {
        LOGGER.info("Updating tables {}", currentDate.toString());
        uploadTableController.refreshTableData(currentDate);
        balanceTableController.refreshTableData(currentDate);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

}
