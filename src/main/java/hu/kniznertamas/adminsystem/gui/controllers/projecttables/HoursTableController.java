package hu.kniznertamas.adminsystem.gui.controllers.projecttables;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ExtendedUploadEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HoursTableController implements Initializable {

    @FXML
    private TableView<ExtendedUploadEntity> hoursTable;

    @FXML
    private Label sumHoursLabel;

    private final GenericDao<UploadEntity> uploadDao;

    public HoursTableController() {
        uploadDao = DaoManager.getInstance().getUploadDao();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerHoursTable(this);
    }

    public void refreshTableData(ProjectsEntity projectsEntity) {
        Stream<UploadEntity> uploadList = uploadDao.findAll().stream();
        List<UploadEntity> filteredList = uploadList.filter(item -> item.getProjectId().equals(projectsEntity.getId())).collect(Collectors.toList());
        Map<Date, List<UploadEntity>> mappedList = filteredList.stream().collect(Collectors.groupingBy(UploadEntity::getCreated));
        List<ExtendedUploadEntity> extendedList = new ArrayList<>();
        final double[] hours = {0};
        mappedList.forEach((k,v)->{
            ExtendedUploadEntity eue = new ExtendedUploadEntity();
            eue.setCreated(k);
            eue.setHour(0.0);
            for(UploadEntity ue : v) {
                eue.setHour(eue.getHour() + ue.getHour());
            }
            extendedList.add(eue);
            hours[0] += eue.getHour();
        });

        hoursTable.setItems(FXCollections.observableArrayList(extendedList));
        hoursTable.refresh();
        sumHoursLabel.setText("Összesen: " + Double.toString(hours[0]) +" óra");
    }

}
