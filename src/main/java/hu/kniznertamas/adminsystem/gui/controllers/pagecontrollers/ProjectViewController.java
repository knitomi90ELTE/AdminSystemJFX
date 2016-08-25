package hu.kniznertamas.adminsystem.gui.controllers.pagecontrollers;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.EntityHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label retentionLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private ComboBox<ProjectsEntity> comboBox;

    public ProjectViewController() {
        loadProjects();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControlerProjects(this);
    }

    public void loadProjectData(ProjectsEntity projectsEntity) {
        nameLabel.setText("Név: " + projectsEntity.getName());
        retentionLabel.setText("Garanciális visszatartás: " + projectsEntity.getRetention().toString() + " Ft");
        noteLabel.setText("Megjegyzés: " + projectsEntity.getNote());
    }

    private void loadProjects() {
        new Thread() {
            @Override
            public void run() {
                GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
                List<ProjectsEntity> allProjects = projectsDao.findAll();
                comboBox.setItems(FXCollections.observableArrayList(allProjects));
                /*comboBox.setCellFactory(new Callback<ListView<ProjectsEntity>, ListCell<ProjectsEntity>>() {
                    @Override
                    public ListCell<ProjectsEntity> call(ListView<ProjectsEntity> param) {
                        return new ListCell<ProjectsEntity>() {
                            @Override
                            public void updateItem(ProjectsEntity item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.getName());
                                    setGraphic(null);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });*/
                EntityHelper.initComboBoxWithProjectsEntity(comboBox);
                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ControllerMediator.getInstance().loadProjectDataToController(newValue));
            }
        }.start();
    }


}
