package hu.kniznertamas.adminsystem.gui.controllers.dataupload;

public interface DataUpload {

	void initDataTable();
	boolean validForm();
	void onSaveAction();
	void onDeleteAction();
	void clearFields();
	
}
