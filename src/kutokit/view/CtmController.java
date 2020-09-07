package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
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
import kutokit.model.CTM;

public class CtmController {

	private MainApp mainApp;
	private File selectedFile;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	
	@FXML private TableView<CTM> contextTable;
	@FXML private TableColumn<CTM, String> CAColumn, casesColumn, contextsColumn;
	@FXML private TableColumn<CTM, Boolean> hazardousColumn;
	@FXML private TableColumn<CTM, Integer> noColumn;
	
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
		this.MakeTable();
	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        // fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
        // minjyo - mac
        fc.setInitialDirectory(new File("/Users/minjyo/eclipse-workspace/KUtoKit/"));
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
	            bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	private void MakeTable() {
		
		// 3. Create Data list ex
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
        mcsData.add(new CTM("Action1", "case1", 1, "c1"));
        mcsData.add(new CTM("Action2", "case2", 2, "c2"));
        mcsData.add(new CTM("Action3", "case3", 3, "c3"));
       
        // 4. Set table row 
        CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
   	 	casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
 	 	noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
 	    contextsColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts"));
 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, Boolean>("hazardous?"));
 	   		
	   	// 5. Put data in table
 	    contextTable.setItems(mcsData);
 	   
	   	CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
	   	casesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
	   	noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());
	   	contextsColumn.setCellValueFactory(cellData -> cellData.getValue().getContextsProperty());
	   	hazardousColumn.setCellValueFactory(cellData -> cellData.getValue().getHazardousProperty());
		
	}
	
	
	
}
