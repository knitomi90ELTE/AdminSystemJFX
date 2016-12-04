package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.helper.WageEntityHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class WageViewController {

    @FXML
    private JFXComboBox<String> yearPicker;

    @FXML
    private JFXComboBox<String> monthPicker;

    @FXML
    private TableView<WageEntityHelper> wageTable;

    public WageViewController() {

    }

    public void init() {
        initYearPicker();
        initMonthPicker();
    }

    private void initYearPicker() {
        yearPicker.getSelectionModel().selectLast();
    }

    private void initMonthPicker() {
        monthPicker.getSelectionModel().selectFirst();
    }

    @FXML
    private void loadTableAction() {
        wageTable.refresh();
    }

}
