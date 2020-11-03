package kutokit.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kutokit.MainApp;
import kutokit.model.ls.LS;

public class LsController {
	@FXML private TextField lossScenarioTextField;
	@FXML private Button addLossScenarioText;
	@FXML private TableView<LS> lossScenarioTableView;
	@FXML private TableColumn<LS, String> linkedUCAColumn, lossFactorColumn, lossScenarioTextColumn;
	
	/*
	 * default constructor
	 */
	public LsController() {
	}
	
	
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
		
	}
	
}