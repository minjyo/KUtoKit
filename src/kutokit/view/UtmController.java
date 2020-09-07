package kutokit.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kutokit.MainApp;

public class UtmController {
	@FXML private TableView contextTable;
	@FXML private TableColumn<String,String> ControlAction, Providing, IncorrectTime, StoppedTooLong, notProviding;


	private MainApp mainApp;

	//constructor
	public UtmController() {

	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void clearTable()
	{

	}

	public void addCase(String CAC,String cases, String no,String context)
	{

	}
}
