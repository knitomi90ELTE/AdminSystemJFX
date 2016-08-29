package hu.kniznertamas.adminsystem.helper;

import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import hu.kniznertamas.adminsystem.Main;
import javafx.scene.control.Label;

public class DialogManager {
	
	private static Map<String, String> classes;
	
	static {
		classes = new HashMap<>();
		classes.put("accept", "dialog-accept");
		classes.put("info", "dialog-info");
		classes.put("error", "dialog-error");
	}
	
	public static void showDialog(String header, String body, String buttonText, String style) {
		JFXDialog dialog = new JFXDialog();
    	JFXDialogLayout content = new JFXDialogLayout();
    	content.setHeading(new Label(header));
    	content.setBody(new Label(body));
    	JFXButton b = new JFXButton(buttonText);
    	b.getStyleClass().add("dialog-accept");
    	b.setOnAction(event -> dialog.close());
    	content.setActions(b);
    	dialog.setContent(content);
    	dialog.setDialogContainer(Main.getInstance().getChangeContent().getRootPane());
    	dialog.show();;
	}
	
}