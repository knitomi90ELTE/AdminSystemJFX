package hu.kniznertamas.adminsystem.gui.elements;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import hu.kniznertamas.adminsystem.Main;
import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import hu.kniznertamas.adminsystem.helper.FXMLLoaderHelper;
import javafx.fxml.FXMLLoader;

public class PopOverElement<T extends PopupAbstractt> {

    private final PopOver popOver;

    public PopOverElement(String fxml, PersistentEntity entity, CallbackInterface callbackFunction) {
        popOver = new PopOver();
        popOver.setAutoHide(false);
        addContent(fxml, entity, callbackFunction);
    }

    private void addContent(String fxml, PersistentEntity entity, CallbackInterface callbackFunction) {
        FXMLLoader loader = FXMLLoaderHelper.getContentNode(fxml);
        try {
            popOver.setContentNode(loader.load());
            T controller = loader.getController();
            controller.loadEntityToFields(entity);
            controller.setCallbackFunction(callbackFunction);
            controller.setParent(popOver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        popOver.show(Main.getInstance().getPrimaryStage());
    }
}
