package hu.kniznertamas.adminsystem.gui.elements;

import com.jfoenix.controls.JFXTextField;

public class NumberTextField extends JFXTextField {

	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(text)) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean validate(String text) {
		return text.matches("\\d*");
	}

}
