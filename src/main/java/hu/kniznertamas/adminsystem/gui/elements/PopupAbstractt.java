package hu.kniznertamas.adminsystem.gui.elements;

import org.controlsfx.control.PopOver;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import javafx.fxml.FXML;

public abstract class PopupAbstractt {

    public abstract void setParent(PopOver parent);

    public abstract void loadEntityToFields(PersistentEntity entity);

    public abstract void setCallbackFunction(CallbackInterface callbackFunction);

    @FXML
    protected abstract void onCancelAction();

}
