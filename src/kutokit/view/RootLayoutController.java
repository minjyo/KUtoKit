package kutokit.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.MainApp;

public class RootLayoutController {

	private MainApp mainApp;
	private TabPane mainTab;
	private Tab cseTab;
	private File selectedFile;
	
	//constructor
	public RootLayoutController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
	private void handleLHCButton() {
		this.mainApp.showLhcView();
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
	
	
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
        	mainApp.openFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File file = mainApp.getContextTableFilePath();
        if (file != null) {
        	mainApp.saveFile(file);
        } else {
            handleSaveAs();
        }
    }
    
    @FXML
    private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveFile(file);
		}
	}
    
    @FXML
    private void handleHelp() {
    	
    }
    
    @FXML
    private void handleDashboard() {
    	this.mainApp.showDashboardView();
    }


}
