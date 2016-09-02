package hu.kniznertamas.adminsystem.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private VBox content;

	public MainController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ControllerMediator.getInstance().registerControllerMainController(this);
		showBasicView();
	}

	public void showBasicView() {
		changeContent("/view/BasicView.fxml");
	}

	public void showNewDataView() {
		changeContent("/view/NewDataView.fxml");
	}
	
	private void changeContent(String fxml) {
		FXMLLoader loader = FXMLLoaderHelper.getContentNode(fxml);
		if(content.getChildren().size() > 0) {
			content.getChildren().remove(0);
		}
		try {
			content.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public StackPane getRoot() {
		return root;
	}

}
