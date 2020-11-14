package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VariablePopUpController {
	 @FXML 
	  private TextField text; 
	  public String value;
	  private Stage stage;
	  private boolean confirmClicked = false;
	  private boolean closeClicked = false;
	  
	  public void setStage(Stage stage) {
		  this.stage = stage;
	  }
	  
	  public void setData() {
		  if(!text.getText().isEmpty()) {
			  value = text.getText();
			  close();
//			  confirmClicked = true;
		  }
	  }
	  
	  public void close() { 
		  Stage pop = (Stage)text.getScene().getWindow(); 
	       pop.close();
//	       closeClicked = true;
	  }
	  
//	  public boolean closeClicked() {
//		  return closeClicked;
//	  }
//	  
//	  public boolean confirmClicked() {
//		  return confirmClicked;
//	  }

}
