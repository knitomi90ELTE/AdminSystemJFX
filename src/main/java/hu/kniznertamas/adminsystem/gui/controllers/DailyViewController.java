package hu.kniznertamas.adminsystem.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Knizner Tamás on 2016. 07. 02..
 */
public class DailyViewController implements Initializable {

    @FXML
    private DatePicker datePicker;


    public DailyViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate ld = LocalDate.now();
        datePicker.setValue(ld);
    }

    public void decreaseDate(){
        LocalDate ld = datePicker.getValue();
        ld = ld.minusDays(1);
        datePicker.setValue(ld);
    }

    public void increaseDate(){
        LocalDate ld = datePicker.getValue();
        ld = ld.plusDays(1);
        datePicker.setValue(ld);
    }


}
