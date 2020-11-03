package kutokit.view;

import javafx.fxml.FXML;
import kutokit.MainApp;

public class DashboardController{
	private MainApp mainApp;
	
	//constructor
	public DashboardController() {
		
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	//click button 1. Define Purpose of the Analysis
	@FXML
	private void toLhcController() {
		this.mainApp.showLhcView();
	}

	//click button 2. Model the Control Structure
	@FXML
	private void toCseController() {
		this.mainApp.showCseView();
	}

	//click button 'Construct the Control Structure with Process Model/variable'
	@FXML
	private void toPmmController() {
		this.mainApp.showPmmView();
	}

	//click button 'Generate Context Table with NuSCR'
	@FXML
	private void toCtmController() {
		this.mainApp.showCtmView();
	}

	//click button 3. Identify Unsafe Control Actions
	@FXML
	private void toUtmController() {
		this.mainApp.showUtmView();
	}
	
	//click button 4. Identify loss scenario
	@FXML
	private void toLsController() {
		this.mainApp.showLsView();
	}
}
