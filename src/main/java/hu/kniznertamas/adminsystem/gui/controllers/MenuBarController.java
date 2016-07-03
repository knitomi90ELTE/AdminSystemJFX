package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.dao.DaoManager;
import hu.kniznertamas.adminsystem.db.dao.GenericDao;
import hu.kniznertamas.adminsystem.db.entity.ProjectsEntity;
import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.gui.elements.CustomMenuItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML
    private Menu usersMenu;

    @FXML
    private Menu projectsMenu;

    public MenuBarController() {
        loadUsers();
        loadProjects();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerMenu(this);
    }

    private void loadUsers(){
        new Thread(){
            @Override
            public void run() {
                GenericDao<UsersEntity> userDao = DaoManager.getInstance().getUserDao();
                List<UsersEntity> allUsers = userDao.findAll();
                for(UsersEntity u : allUsers){
                    CustomMenuItem cmi = new CustomMenuItem(u.getName(), u.getId());
                    cmi.setOnAction(event -> ControllerMediator.getInstance().loadUserDataToController(userDao.findById(cmi.getItemID())));
                    usersMenu.getItems().add(cmi);
                }
            }
        }.start();
    }

    private void loadProjects(){
        new Thread(){
            @Override
            public void run() {
                GenericDao<ProjectsEntity> projectsDao = DaoManager.getInstance().getProjectsDao();
                List<ProjectsEntity> allProjects = projectsDao.findAll();
                for(ProjectsEntity p : allProjects){
                    CustomMenuItem cmi = new CustomMenuItem(p.getName(), p.getId());
                    cmi.setOnAction(event -> System.out.println(cmi.getText()));
                    projectsMenu.getItems().add(cmi);
                }
            }
        }.start();
    }
}
