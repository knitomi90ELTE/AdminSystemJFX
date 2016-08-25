package hu.kniznertamas.adminsystem;

import hu.kniznertamas.adminsystem.helper.ChangeContent;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static Main instance;
    private ChangeContent changeContent;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public ChangeContent getChangeContent() {
        return changeContent;
    }

    @Override
    public void start(Stage primaryStage) {
        LOGGER.info("Application started");
        changeContent = new ChangeContent(instance, primaryStage);
        changeContent.replaceSceneContent("/view/Main.fxml");
        primaryStage.setTitle("Adminisztrációs rendszer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
