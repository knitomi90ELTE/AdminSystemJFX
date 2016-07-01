package hu.kniznertamas.adminsystem.helper;

import hu.kniznertamas.adminsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Knizner Tamás on 2016. 07. 01..
 */
public class ChangeContent {

    private final Main instance;
    private final Stage stage;

    public ChangeContent(Main instance, Stage stage) {
        this.instance = instance;
        this.stage = stage;
    }

    public void replaceSceneContent(String fxml) {
        try {
            Parent page = (Parent) FXMLLoader.load(instance.getClass().getResource(fxml), null, new JavaFXBuilderFactory());
            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(page);
            }
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
