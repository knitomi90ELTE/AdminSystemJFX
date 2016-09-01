package hu.kniznertamas.adminsystem.helper;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXComboBox;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.BalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedBalanceEntity;
import hu.kniznertamas.adminsystem.db.entity.ExtendedUploadEntity;
import hu.kniznertamas.adminsystem.db.entity.NamedEntity;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.StatusEntity;
import hu.kniznertamas.adminsystem.db.entity.UploadEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class EntityHelper {

    private static final GenericDao<StatusEntity> statusDao = DaoManager.getInstance().getStatusDao();
    private static final GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
    private static final GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();

    public static List<ExtendedBalanceEntity> createExtendedBalanceEntityList(List<BalanceEntity> list) {
        List<ExtendedBalanceEntity> extendedList = new ArrayList<>();
        for (BalanceEntity be : list) {
            ExtendedBalanceEntity ebe = new ExtendedBalanceEntity(be);
            ebe.setStatus_name(statusDao.findById(be.getStatusId()).getName());
            if ("project".equals(ebe.getModelName())) {
                ProjectsEntity pe = projectsDao.findById(be.getModelId());
                ebe.setModel(pe);
                ebe.setDisplay_name(pe.getName());
            } else if ("user".equals(ebe.getModelName())) {
                UsersEntity ue = userDao.findById(be.getModelId());
                ebe.setModel(ue);
                ebe.setDisplay_name(ue.getName());
            } else {
                ebe.setModel(null);
                ebe.setDisplay_name("");
            }
            extendedList.add(ebe);
        }
        return extendedList;

    }

    public static List<ExtendedUploadEntity> createExtendedUploadEntityList(List<UploadEntity> list) {
        List<ExtendedUploadEntity> extendedList = new ArrayList<>();
        for (UploadEntity ue : list) {
            ExtendedUploadEntity eue = new ExtendedUploadEntity(ue);
            eue.setProject_name(projectsDao.findById(ue.getProjectId()).getName());
            eue.setUser_name(userDao.findById(ue.getUserId()).getName());
            extendedList.add(eue);
        }
        return extendedList;
    }
    
    public static <T extends NamedEntity> void initComboBoxWithEntity(JFXComboBox<T> comboBox) {
    	comboBox.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> param) {
                return new ListCell<T>() {
                    @Override
                    public void updateItem(T item, boolean empty) {
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
        });
    }

}
