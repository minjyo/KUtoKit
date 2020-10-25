package kutokit.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kutokit.MainApp;

public class RoadMap{
	private MainApp mainApp;
	
	//constructor
	public RoadMap() {
		
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	//click button 1. Define Purpose of the Analysis
	private void toLhcController() {
		this.mainApp.showLhcView();
	}
	
	@FXML
	//click button 2. Model the Control Structure
	private void toCseController() {
		this.mainApp.showCseView();
	}
	
	@FXML
	//click button 'Construct the Control Structure with Process Model/variable'
	private void toPmmController() {
		this.mainApp.showPmmView();
	}
	
	@FXML
	//click button 'Generate Context Table with NuSCR'
	private void toCtmController() {
		this.mainApp.showCtmView();
	}
	
	@FXML
	//click button 3. Identify Unsafe Control Actions
	private void toUtmController() {
		this.mainApp.showUtmView();
	}
}
