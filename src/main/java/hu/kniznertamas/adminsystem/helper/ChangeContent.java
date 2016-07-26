package hu.kniznertamas.adminsystem.helper;

import hu.kniznertamas.adminsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeContent {

    private final Main instance;
    private final Stage stage;

    public ChangeContent(Main instance, Stage stage) {
        this.instance = instance;
        this.stage = stage;
    }

    public Stage getMainStage(){
        return stage;
    }

    public void replaceSceneContent(String fxml) {
        try {
            Parent page = FXMLLoader.load(instance.getClass().getResource(fxml), null, new JavaFXBuilderFactory());
            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(page);
            }
            //stage.setMaximized(true);
            //stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FXMLLoader getContentNode(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(instance.getClass().getResource(fxml));
        return loader;
    }

}
