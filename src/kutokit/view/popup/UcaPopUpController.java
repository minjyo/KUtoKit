package kutokit.view.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UcaPopUpController {

	@FXML
	  private TextField modifyText;
	  private String  ucatext;

	  public UcaPopUpController() {

	  }

	  public void cancel() {
		  Stage pop = (Stage)modifyText.getScene().getWindow();
	       pop.close();
	  }

	  public void confirm(){
		  ucatext = modifyText.getText();
		  Stage pop = (Stage)modifyText.getScene().getWindow();
	       pop.close();
	  }

	public String getModifyText() {
		// TODO Auto-generated method stub
		return ucatext;
	}

}
