package hu.kniznertamas.adminsystem;

import hu.kniznertamas.adminsystem.helper.ChangeContent;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

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
    public void start(Stage stage) {
        changeContent = new ChangeContent(instance, stage);
        changeContent.replaceSceneContent("/view/Main.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
