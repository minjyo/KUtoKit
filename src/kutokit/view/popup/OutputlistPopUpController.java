package kutokit.view.popup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


public class OutputlistPopUpController implements Initializable{

	@FXML
	private ChoiceBox<String> outputList;
	public String output;
	ObservableList<String> list =
			FXCollections.observableArrayList();
	
	public OutputlistPopUpController() {
		output = "output variable";
		list = FXCollections.observableArrayList("f_LO_SG1_LEVEL_Trip_Out", "f_HI_LOG_POWER_Trip_Out"
				,"f_VAR_OVER_PWR_Trip_Out");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		outputList.setItems(list);
	}
	
	@FXML
	public void selectValue() {
		output = outputList.getSelectionModel().getSelectedItem();
		Stage pop = (Stage)outputList.getScene().getWindow(); 
	    pop.close();
	}
	
}
