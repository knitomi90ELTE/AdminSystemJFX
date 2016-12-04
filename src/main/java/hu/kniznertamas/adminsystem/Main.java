package hu.kniznertamas.adminsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.kniznertamas.adminsystem.config.Config;
import hu.kniznertamas.adminsystem.gui.ScreensController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) {
        LOGGER.info("Application started");
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            ScreensController screensController = context.getBean(ScreensController.class);
            screensController.init(primaryStage);
            screensController.showPrimaryStage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
