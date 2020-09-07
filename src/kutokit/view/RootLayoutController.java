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

        // 확장자 필터를 설정한다.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Save File Dialog를 보여준다.
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
        	mainApp.loadContextTableDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File ContextFile = mainApp.getContextTableFilePath();
        if (ContextFile != null) {
        	mainApp.saveContextTableDataToFile(ContextFile);
        } else {
            handleSaveAs();
        }
    }
    
    /**
     * FileChooser를 열어서 사용자가 저장할 파일을 선택하게 한다.
     */
    @FXML
    private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// 확장자 필터를 설정한다.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Save File Dialog를 보여준다.
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// 정확한 확장자를 가져야 한다.
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveContextTableDataToFile(file);
		}
	}


}
