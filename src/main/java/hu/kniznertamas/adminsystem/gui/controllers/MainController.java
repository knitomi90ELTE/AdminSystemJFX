package hu.kniznertamas.adminsystem.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	@FXML
	private StackPane root;
	
    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

	public StackPane getRoot() {
		return root;
	}

}
