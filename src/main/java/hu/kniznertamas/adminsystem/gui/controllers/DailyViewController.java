package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DailyViewController implements Initializable {

    @FXML
    private DatePicker datePicker;

    private LocalDate currentDate;

    public DailyViewController() {

    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate ld = LocalDate.now();
        datePicker.setValue(ld);
        currentDate = ld;
        updateTables();
    }

    public void decreaseDate(){
        LocalDate ld = datePicker.getValue();
        ld = ld.minusDays(1);
        datePicker.setValue(ld);
        currentDate = ld;
    }

    public void increaseDate(){
        LocalDate ld = datePicker.getValue();
        ld = ld.plusDays(1);
        datePicker.setValue(ld);
        currentDate = ld;
    }

    public void onDateChangeAction(ActionEvent event){
        currentDate = datePicker.getValue();
        updateTables();
    }

    private void updateData(boolean updateTables, boolean setValue,  LocalDate ld){
        //TODO: fenti metódusok refaktorálása, mert csúnya
    }

    private void updateTables(){
        ControllerMediator.getInstance().refreshDailyTableData(currentDate);
    }

}
