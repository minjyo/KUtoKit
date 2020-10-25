package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class CAPopUpController {

	  @FXML 
	  private TextField text1;
	  @FXML
	  private TextField text2;
	  public String controller;
	  public String controlled;
	  
	  public CAPopUpController() {
		  controller = "Controller";
		  controlled = "Controlled";
	  }
	  
	  public void setData() {
		  controller = text1.getText();
		  controlled = text2.getText();
		  close();
	  }
	  
	  public void close() { 
	       Stage pop = (Stage)text1.getScene().getWindow(); 
	       pop.close();
	  }


}
