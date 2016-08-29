package hu.kniznertamas.adminsystem.helper;

import hu.kniznertamas.adminsystem.Main;
import hu.kniznertamas.adminsystem.gui.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeContent {

	private final Main instance;
	private final Stage stage;

	private StackPane rootPane = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeContent.class);

	public ChangeContent(Main instance, Stage stage) {
		this.instance = instance;
		this.stage = stage;
	}

	public Stage getMainStage() {
		return stage;
	}

	public void replaceSceneContent(String fxml) {
		if (fxml == null) {
			LOGGER.error("Given fxml is null.");
			return;
		}
		try {
			LOGGER.info("Changing content to {}", fxml);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(instance.getClass().getResource(fxml));
			Parent page = loader.load();
			if (rootPane == null && loader.getController() instanceof MainController) {
				LOGGER.info("RootPane set.");
				MainController mc = loader.getController();
				rootPane = mc.getRoot();
			}
			Scene scene = stage.getScene();
			page.getStylesheets().add(instance.getClass().getResource("/style/jfoenix-fonts.css").toExternalForm());
			page.getStylesheets().add(instance.getClass().getResource("/style/main.css").toExternalForm());
			page.getStylesheets().add(instance.getClass().getResource("/style/jfoenix-design.css").toExternalForm());
			if (scene == null) {
				scene = new Scene(page);
				stage.setScene(scene);
			} else {
				stage.getScene().setRoot(page);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	public FXMLLoader getContentNode(String fxml) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(instance.getClass().getResource(fxml));
		return loader;
	}

	public StackPane getRootPane() {
		return rootPane;
	}

}
