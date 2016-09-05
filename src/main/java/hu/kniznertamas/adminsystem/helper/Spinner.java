package hu.kniznertamas.adminsystem.helper;

import com.jfoenix.controls.JFXSpinner;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Spinner {

    private JFXSpinner spinner;
    private Stage dialogStage;
    private ProgressBar progressBar;


    public Spinner() {
        initStage();
        initSpinner();
        initScene();
    }

    public void activateProgressBar(final Task<?> task) {
        progressBar = new ProgressBar();
        task.setOnSucceeded(event -> dialogStage.close());
        progressBar.setProgress(-1F);
        progressBar.progressProperty().bind(task.progressProperty());
        Thread thread = new Thread(task);
        thread.start();
        dialogStage.show();
    }

    private void initScene() {
        Scene scene = new Scene(spinner);
        scene.setFill(Color.TRANSPARENT);
        dialogStage.setScene(scene);
    }

    private void initStage() {
        dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void initSpinner() {
        spinner = new JFXSpinner();
        spinner.setStartingAngle(0.0);
        spinner.setRadius(50);
        spinner.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
    }

}

