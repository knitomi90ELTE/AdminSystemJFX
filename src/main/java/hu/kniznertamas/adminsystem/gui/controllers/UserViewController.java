package hu.kniznertamas.adminsystem.gui.controllers;

import hu.kniznertamas.adminsystem.db.entity.UsersEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Knizner Tamás on 2016. 07. 02..
 */
public class UserViewController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label wageLabel;

    @FXML
    private Label noteLabel;

    public UserViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerControllerUser(this);
    }

    public void loadUserData(UsersEntity usersEntity) {
        nameLabel.setText("Név: " + usersEntity.getName());
        wageLabel.setText("Órabér: " + usersEntity.getWage().toString());
        noteLabel.setText("Megjegyzés: " + usersEntity.getNote());

    }

}
