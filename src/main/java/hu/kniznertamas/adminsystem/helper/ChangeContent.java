package hu.kniznertamas.adminsystem.helper;

import hu.kniznertamas.adminsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeContent {

    private final Main instance;
    private final Stage stage;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeContent.class);

    public ChangeContent(Main instance, Stage stage) {
        this.instance = instance;
        this.stage = stage;
    }

    public Stage getMainStage(){
        return stage;
    }

    public void replaceSceneContent(String fxml) {
    	if(fxml == null) {
    		return;
    	}
        try {
            LOGGER.info("Changing content to {}", fxml);
            Parent page = FXMLLoader.load(instance.getClass().getResource(fxml), null, new JavaFXBuilderFactory());
            Scene scene = stage.getScene();
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

}
