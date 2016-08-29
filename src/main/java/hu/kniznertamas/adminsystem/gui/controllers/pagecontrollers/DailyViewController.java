package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import hu.kniznertamas.adminsystem.Main;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.DialogManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DailyViewController implements Initializable {

    @FXML
    private JFXDatePicker datePicker;

    private LocalDate currentDate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyViewController.class);

    public DailyViewController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate ld = LocalDate.now();
        datePicker.setValue(ld);
        currentDate = ld;
        ControllerMediator.getInstance().registerControllerDailyView(this);
        updateTables();
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

    @SuppressWarnings("unused")
	private void updateData(boolean updateTables, boolean setValue, LocalDate ld) {
        //TODO: fenti metódusok refaktorálása, mert csúnya
    }

    private void updateTables() {
        LOGGER.info("Updating tables {}", currentDate.toString());
        ControllerMediator.getInstance().refreshDailyTableData(currentDate);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
    
    public void showDialog() {
    	DialogManager.showDialog("Header", "A Lorem Ipsum egy egyszerû szövegrészlete, szövegutánzata a betûszedõ és nyomdaiparnak. "
    			+ "A Lorem Ipsum az 1500-as évek óta standard szövegrészletként szolgált az iparban.", "OK", "accept");
    }

}
