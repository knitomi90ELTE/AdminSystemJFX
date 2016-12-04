package hu.kniznertamas.adminsystem.gui;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jfoenix.controls.JFXDecorator;

import hu.kniznertamas.adminsystem.helper.FXMLLoaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Service
public class ScreensController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreensController.class);

    private Stage primaryStage;

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showPrimaryStage() {
        LOGGER.info("Initializing primary stage...");
        Locale.setDefault(Locale.forLanguageTag("hu_HU"));
        FXMLLoader loader = FXMLLoaderHelper.getContentNode("/view/Main.fxml");
        try {
            setStageProperties(loader.load());
            primaryStage.show();
            // MainController mc = loader.getController();
            // mc.showBasicView();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void setStageProperties(Parent pageToLoad) {
        JFXDecorator decorator = new JFXDecorator(primaryStage, pageToLoad);
        decorator.setCustomMaximize(true);
        decorator.setMaximized(true);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/style/jfoenix-fonts.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/style/jfoenix-design.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adminisztrációs rendszer");
    }

}
