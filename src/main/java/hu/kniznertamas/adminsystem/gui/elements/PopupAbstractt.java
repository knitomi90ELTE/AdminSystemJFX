package hu.kniznertamas.adminsystem.gui.elements;

import hu.kniznertamas.adminsystem.db.entity.PersistentEntity;
import hu.kniznertamas.adminsystem.helper.CallbackInterface;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

import java.time.LocalDate;

public abstract class PopupAbstractt {

    public abstract void setParent(PopOver parent);

    public abstract void loadEntityToFields(PersistentEntity entity);

    public abstract void setCallbackFunction(CallbackInterface callbackFunction);

    @FXML
    protected abstract void onCancelAction();

}
