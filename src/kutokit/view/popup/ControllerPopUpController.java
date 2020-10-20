package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class ControllerPopUpController {

	  @FXML 
	  private TextField text;
	  public String name;
	  
	  public ControllerPopUpController() {
		  name = "Controller Name";
	  }
	  
	  public void setData() {
		  name = text.getText();
		  close();
	  }
	  
	  public void close() { 
	       Stage pop = (Stage)text.getScene().getWindow(); 
	       pop.close();
	  }


}
