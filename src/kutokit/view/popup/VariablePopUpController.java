package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VariablePopUpController {
	 @FXML 
	  private TextField text; 
	  public String value; 
	  
	  public void setData() {
		  value = text.getText();
		  close();
	  }
	  
	  public void close() { 
	       Stage pop = (Stage)text.getScene().getWindow(); 
	       pop.close();
	  }

}
