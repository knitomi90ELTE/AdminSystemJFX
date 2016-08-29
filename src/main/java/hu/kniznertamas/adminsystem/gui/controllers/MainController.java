package hu.kniznertamas.adminsystem.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends RootController implements Initializable {

	@FXML
	private StackPane root;
	
    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
	public StackPane getRoot() {
		return root;
	}

}
