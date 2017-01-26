package hu.kniznertamas.adminsystem.gui.controllers.projecttables;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedBalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class FinancesTableController implements Initializable {

    private final GenericDao<BalanceEntity> balanceDao;
    private final GenericDao<StatusEntity> statusDao;
    private final GenericDao<ProjectsEntity> projectsDao;

    @FXML
    private TableView<ExtendedBalanceEntity> financesTable;

    @FXML
    private Label expensesLabel;

    @FXML
    private Label incomesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerFinancesTable(this);
    }

    public FinancesTableController() {
        balanceDao = DaoManager.getInstance().getBalanceDao();
        statusDao = DaoManager.getInstance().getStatusDao();
        projectsDao = DaoManager.getInstance().getProjectsDao();
    }

    public void refreshTableData(ProjectsEntity projectsEntity) {
        Stream<BalanceEntity> uploadList = balanceDao.findAll().stream();
        List<BalanceEntity> filteredList = uploadList.filter(
                item -> "project".equals(item.getModelName()) && projectsEntity.getId().equals(item.getModelId()))
                .collect(Collectors.toList());
        List<Integer> incomeIndexes = Arrays.asList(3, 17, 18);
        int allIncome = 0;
        int allExpense = 0;
        List<ExtendedBalanceEntity> extendedList = new ArrayList<>();
        for (BalanceEntity be : filteredList) {
            ExtendedBalanceEntity ebe = new ExtendedBalanceEntity(be);
            ebe.setStatus_name(statusDao.findById(be.getStatusId()).getName());
            ProjectsEntity pe = projectsDao.findById(be.getModelId());
            ebe.setModel(pe);
            ebe.setDisplay_name(pe.getName());
            extendedList.add(ebe);
            if (incomeIndexes.contains(be.getStatusId())) {
                allIncome += be.getBrutto();
            } else {
                allExpense += be.getBrutto();
            }
        }
        initCellFactory();
        financesTable.setItems(FXCollections.observableArrayList(extendedList));
        financesTable.refresh();
        expensesLabel.setText("Összes kiadás: " + allExpense + " Ft (Brutto)");
        incomesLabel.setText("Összes bevétel: " + allIncome + " Ft (Brutto)");
    }

    private void initCellFactory() {
        financesTable.setRowFactory(row -> new TableRow<ExtendedBalanceEntity>() {
            @Override
            public void updateItem(ExtendedBalanceEntity item, boolean empty) {
                if (empty) {
                    return;
                }
                List<Integer> incomeIndexes = Arrays.asList(3, 17, 18);
                if (incomeIndexes.contains(item.getStatusId())) {
                    setStyle("-fx-background-color: rgba(0, 255, 0, 0.3)");
                }
            }
        });
    }
}
