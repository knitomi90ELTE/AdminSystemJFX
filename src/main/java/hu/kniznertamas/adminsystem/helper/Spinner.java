package hu.kniznertamas.adminsystem.helper;

public class Spinner {

}
/*
package test;

import com.jfoenix.controls.JFXSpinner;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            ProgressForm pForm = new ProgressForm();

            // In real life this task would do something useful and return 
            // some meaningful result:
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    for (int i = 0; i < 10; i++) {
                        updateProgress(i, 10);
                        Thread.sleep(200);
                    }
                    updateProgress(10, 10);
                	for(int i = 0;  i < 5; i++) {
                		Thread.sleep(1000);
                	}
                    return null ;
                }
            };

            // binds progress of progress bars to progress of task:
            pForm.activateProgressBar(task);

            // in real life this method would get the result of the task
            // and update the UI based on its value:
            task.setOnSucceeded(event -> {
                pForm.getDialogStage().close();
                startButton.setDisable(false);
            });

            startButton.setDisable(true);
            pForm.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
        });

        StackPane root = new StackPane(startButton);
        Scene scene = new Scene(root, 350, 75);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static class ProgressForm {
    	
    	private final JFXSpinner spinner = new JFXSpinner();
    	
        private final Stage dialogStage;
        //private final ProgressBar pb = new ProgressBar();
        //private final ProgressIndicator pin = new ProgressIndicator();

        public ProgressForm() {
            dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            spinner.setStartingAngle(0.0);
            spinner.setRadius(100);
            spinner.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
            
            // PROGRESS BAR
            final Label label = new Label();
            label.setText("alerto");

            pb.setProgress(-1F);
            pin.setProgress(-1F);

            final HBox hb = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(pb, pin);

            Scene scene = new Scene(spinner);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(scene);
        }

        public void activateProgressBar(final Task<?> task)  {
            pb.progressProperty().bind(task.progressProperty());
            pin.progressProperty().bind(task.progressProperty());
            dialogStage.show();
        }

        public Stage getDialogStage() {
            return dialogStage;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 *
 */
