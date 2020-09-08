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
		this.mainApp.showFSView();
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

        // �솗�옣�옄 �븘�꽣瑜� �꽕�젙�븳�떎.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Save File Dialog瑜� 蹂댁뿬以��떎.
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
     * FileChooser瑜� �뿴�뼱�꽌 �궗�슜�옄媛� ���옣�븷 �뙆�씪�쓣 �꽑�깮�븯寃� �븳�떎.
     */
    @FXML
    private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// �솗�옣�옄 �븘�꽣瑜� �꽕�젙�븳�떎.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Save File Dialog瑜� 蹂댁뿬以��떎.
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// �젙�솗�븳 �솗�옣�옄瑜� 媛��졇�빞 �븳�떎.
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveContextTableDataToFile(file);
		}
	}


}
