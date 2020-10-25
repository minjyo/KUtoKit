package kutokit.view;

import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import kutokit.MainApp;
import kutokit.model.LHC;

public class LhcController {

	private MainApp mainApp;
	private LHC lhc;
	
	@FXML private TableView lossTable;
	@FXML private TableColumn lossIndexColumn, lossTextColumn;
	@FXML private TableView hazardTable;
	@FXML private TableColumn hazardIndexColumn, hazardTextColumn, hazardLinkColumn;
	@FXML private TableView constraintTable;
	@FXML private TableColumn constIndexColumn, constTextColumn, constLinkColumn;
	@FXML private TextField typeTextField;
	@FXML private ChoiceBox<String> typeChoiceBox;
	@FXML private Button addRowButton;
	
	
	
	//constructor
	public LhcController() {
		
	}
	
	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	//choose type
	@FXML
	public void setType(LHC lhc) {
		if(typeChoiceBox.getValue().equals("loss")) {
			lhc.setType("loss");
		}else if(typeChoiceBox.getValue().equals("hazard")) {
			lhc.setType("hazard");
		}else if(typeChoiceBox.getValue().equals("constraint")) {
			lhc.setType("constraint");
		}
	}
	
	//add text of loss, hazard, constraints
	@FXML
	public void setText(LHC lhc) {
		Scanner input = new Scanner(System.in);
		String inputText = input.next();
		
		lhc.setText(inputText);
	}
	
	//add row into table
	@FXML
	public void addRow() {
		
	}
	
	//select from choice box
	@FXML
	private void typeChosen() {
		ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Loss", "Hazard", "Safety Constraint");
		
		typeChoiceBox.setItems(choiceBoxList);
		typeChoiceBox.setValue("type");
		typeChoiceBox.setTooltip(new Tooltip("Select Type"));
		
		typeChoiceBox.getSelectionModel();
	}
	
	private void setText() {
		
	}
	
	
}
