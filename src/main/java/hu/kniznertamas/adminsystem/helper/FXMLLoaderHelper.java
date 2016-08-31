package hu.kniznertamas.adminsystem.helper;

import hu.kniznertamas.adminsystem.Main;
import javafx.fxml.FXMLLoader;

public class FXMLLoaderHelper {

	public static FXMLLoader getContentNode(String fxml) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(fxml));
		return loader;
	}
	
}
