package kutokit.view;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import kutokit.MainApp;

public class DashboardController implements Initializable{
	private MainApp mainApp;
	
	@FXML
	public Button cseBtn;
	public Button pmmBtn;

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
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		//CSE
//		if(mainApp.lhcDataStore.getLossTableList().isEmpty() || mainApp.lhcDataStore.getHazardTableList().isEmpty() || mainApp.lhcDataStore.getConstraintTableList().isEmpty()) {
//			cseBtn.setDisable(true);
//		}else {
//			cseBtn.setDisable(false);
//		}

		//PMM
//		if(MainApp.components.getControllers().isEmpty() || MainApp.components.getControlActions().isEmpty() ) {
//			pmmBtn.setDisable(true);
//		}else {
//			pmmBtn.setDisable(false);
//		}
	}
	
}
