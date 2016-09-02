package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

import hu.kniznertamas.adminsystem.helper.DialogManager;

public class Validation {

	public static boolean isValid(DataUpload controller) {
		boolean isValid = true;
		if(!controller.validForm()) {
			DialogManager.showDialog("Hiba", "Hiba a bevitt adatokban!", "Ok", "error");
            controller.clearFields();
            isValid = false;
		}
		return isValid;
	}
	
}
