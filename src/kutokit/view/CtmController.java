package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.MainApp;
import kutokit.model.ContextTableDataModel;

public class CtmController {

	private MainApp mainApp;
	private File selectedFile;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	
	@FXML private TableView contextTable;
	@FXML private TableColumn CAColumn, casesColumn, noColumn, contextsColumn, hazardousColumn;
	
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
        
        // 확장자 제한
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile);  // 선택한 경로 출력
	        //System.out.println(selectedFile.getName());
	        filename.setText(selectedFile.getName());
        }
    }
	
	@FXML
	public void ApplyFile() throws IOException {
		if(selectedFile != null) {
			AddFile.getChildren().clear();
			
			// 1. Read MCS File
	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            
	            byte [] buffer = new byte[512];
	            while((bis.read(buffer)) != -1) {
	            	System.out.println(new String(buffer));
	            }    
	           
	            /*
	             * 2. Add Parsing File
	             * 
	             */
	            
		    // 3. Create Data list ex)
		   		ObservableList<ContextTableDataModel> myTable = FXCollections.observableArrayList(
		   		   new ContextTableDataModel("Action1", "case1", 1, "c1"),
		   		   new ContextTableDataModel("Action2", "case2", 2 , "c2"),
		   		   new ContextTableDataModel("Action3", "case3", 3 ,"c3")
		   		);
		   		
		   	// 4. Parsing Data & Connecting with table
		   		CAColumn.setCellValueFactory(new PropertyValueFactory<ContextTableDataModel, String>("controlAction"));
		   		casesColumn.setCellValueFactory(new PropertyValueFactory<ContextTableDataModel, String>("cases"));
		   		noColumn.setCellValueFactory(new PropertyValueFactory<ContextTableDataModel, Integer>("no"));
		   		contextsColumn.setCellValueFactory(new PropertyValueFactory<ContextTableDataModel, String>("contexts"));
		   		hazardousColumn.setCellValueFactory(new PropertyValueFactory<ContextTableDataModel, ComboBox>("hazardous"));
		   		
		        contextTable.setItems(myTable);
	         
	            bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}
	
}
