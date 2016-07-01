package hu.kniznertamas.adminsystem.gui.elements;

import javafx.scene.control.MenuItem;

public class CustomMenuItem extends MenuItem {

    private Integer itemID;

    public CustomMenuItem(String text, Integer itemID) {
        super(text);
        this.itemID = itemID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }
}
