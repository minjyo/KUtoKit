package kutokit.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kutokit.MainApp;

public class FSController {

	private MainApp mainApp;
	
	@FXML private Button addRow;
	@FXML private TableView firstTable;
	@FXML private TextField text;
	@FXML private ChoiceBox<String> cb;
	private ObservableList<String> cbList = FXCollections.observableArrayList("Loss", "Hazard", "Safety Constraint");
	
	
	//constructor
	public FSController() {
		
	}
	
	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	//add text of loss, hazard, constraints
	@FXML
	public void addText() {
		
	}
	
	//add row into table
	@FXML
	public void addRow() {
		
	}
	
	//select from choice box
	@FXML
	private void setCB() {
		cb.setItems(cbList);
		cb.setValue(".");
	}
}
