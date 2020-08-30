package kutokit.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import kutokit.MainApp;

public class RootLayoutController {

	private MainApp mainApp;
	private TabPane mainTab;
	private Tab cseTab;
	
	//constructor
	public RootLayoutController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
	private void handleCseButton() {
		this.mainApp.showCseView();
	}
	
	@FXML
	private void handlePmmButton() {
		this.mainApp.showPmmView();
	}
	
	@FXML
	private void handleCtmButton() {
		this.mainApp.showCtmView();
	}
	
	@FXML
	private void handleUtmButton() {
		this.mainApp.showUtmView();
	}
}
