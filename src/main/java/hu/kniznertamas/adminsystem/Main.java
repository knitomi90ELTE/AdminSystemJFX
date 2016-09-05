package hu.kniznertamas.adminsystem;

import java.io.IOException;
import java.util.Locale;

import hu.kniznertamas.adminsystem.helper.Spinner;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXDecorator;

import hu.kniznertamas.adminsystem.gui.controllers.mediator.ControllerMediator;
import hu.kniznertamas.adminsystem.helper.FXMLLoaderHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private Stage primaryStage;
	private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }	
	
	@Override
	public void start(Stage primaryStage) {
		LOGGER.info("Application started");
		Locale.setDefault(Locale.forLanguageTag("hu_HU"));
		showPrimaryStage(primaryStage);
		this.primaryStage = primaryStage;
	}

	private void showPrimaryStage(Stage primaryStage) {
		LOGGER.info("Initializing primary stage...");
		FXMLLoader loader = FXMLLoaderHelper.getContentNode("/view/Main.fxml");
		try {
			setStageProperties(primaryStage, loader.load());
			primaryStage.show();

            Spinner sp = new Spinner();
            sp.activateProgressBar(new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(2000);
                    ControllerMediator.getInstance().loadAllData();
                    updateProgress(9, 10);
                    Thread.sleep(2000);
                    updateProgress(10, 10);
                    return null;
                }
            });

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	private void setStageProperties(Stage primaryStage, Parent pageToLoad) {
		JFXDecorator decorator = new JFXDecorator(primaryStage, pageToLoad);
		decorator.setCustomMaximize(true);
		Scene scene = new Scene(decorator);
		scene.getStylesheets().add(getClass().getResource("/style/jfoenix-fonts.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/style/main.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/style/jfoenix-design.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Adminisztrációs rendszer");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
