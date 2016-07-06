package hu.kniznertamas.adminsystem.gui.controllers.projecttables;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class FinancesTableController implements Initializable {

    @FXML
    private TableView financesTable;

    //Megjeleníteni:
    //összes bevétel, kiadás
    //
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControlerFinancesTable(this);
    }
}
