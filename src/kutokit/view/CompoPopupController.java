package kutokit.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class CompoPopupController {

	  @FXML 
	  private TextField text;
	  public String name;
	  
	  public CompoPopupController() {
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
