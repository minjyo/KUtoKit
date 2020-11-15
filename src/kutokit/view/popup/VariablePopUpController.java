package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VariablePopUpController {
	 @FXML 
	 private TextField textField;
	 public String value;
	 @FXML
	 private Button confirmButton, cancelButton;
	 
	 public boolean confirmClicked = false, closeClicked = false;
	  
	 public VariablePopUpController() {
		 if(textField.getText() != null) { 
			 confirmButton.setOnMouseClicked(MouseEvent ->{
				 value = textField.getText();
				 confirmClicked = true;
			 });
		 }
		 
		 cancelButton.setOnMouseClicked(MouseEvent -> {
			 closeClicked = true;
		 });
	 }
}
