package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import hu.kniznertamas.adminsystem.gui.controllers.RootController;

public class DataUploadController extends RootController implements Initializable {

	@FXML
	private StackPane root;
	
    public DataUploadController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
	public StackPane getRoot() {
		return root;
	}

}
