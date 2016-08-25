package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import com.jfoenix.controls.JFXComboBox;
import hu.kniznertamas.adminsystem.helper.WageEntityHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class WageViewController implements Initializable {

    @FXML
    private JFXComboBox<String> yearPicker;

    @FXML
    private JFXComboBox<String> monthPicker;

    @FXML
    private TableView<WageEntityHelper> wageTable;

    public WageViewController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    }


}
