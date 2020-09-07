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
	private ObservableList<ContextTableDataModel> myTable;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	
	@FXML private TableView<CTM> contextTable;
	@FXML private TableColumn<CTM, String> CAColumn, casesColumn, contextsColumn;
	@FXML private TableColumn<CTM, Boolean> hazardousColumn;
	@FXML private TableColumn<CTM, Integer> noColumn;
	
	private String[] contexts = new String[10];
	// constructor
	public CtmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
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
	        //System.out.println(selectedFile);  // �꽑�깮�븳 寃쎈줈 異쒕젰
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
	            String temp="";
	            while((bis.read(buffer)) != -1) {
	            	temp = new String(buffer);
	            }    
	           
	            //2. Add Parsing File
	            String[] temps = new String[10];
	            temps = temp.split(" Λ ");
	            
	            this.ParseMSC(temps);
	            
	            
	            this.MakeTable();

	            bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private void ParseMSC(String[] temps) {
		//MSC ex 
//		detect_term≤0.1sec Λ 
//		detect_length≥1m Λ
//		sensor_error=false Λ 
//		malfunc_check_clear=true Λ 
//		path_check=true Λ 
//		gps_one=true
		int i=0;
		while(i < temps.length) {
			if(temps[i].contains("≤")) { 
				String[] splits = temps[i].split("≤");
				
				if(splits.length > 2) { // a ≤ x ≤ b
					contexts[i] = splits[0] + " <= x <= " +  splits[2];
				}else { // x ≤ a 
					contexts[i] = "x <= " +  splits[1];
				}
			}else if(temps[i].contains("≥")) {
				String[] splits = temps[i].split("≥");
				contexts[i] = "x >= " +  splits[1];
			}else if(temps[i].contains("=")) { // x = true or false
				String[] splits = temps[i].split("=");
				contexts[i] = splits[1];
			}else {
				contexts[i] = "N/A";
			}
			i++;
		}
		
	}
	
	private void MakeTable() {
		
		// 3. Create Data list ex
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
		
		int i=0;
		while(i < contexts.length) {
			 mcsData.add(new CTM("Action", "case", i, contexts[i]));
			 i++;
		}  
       
        // 4. Set table row 
        CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
   	 	casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
 	 	noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
 	    contextsColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts"));
 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, Boolean>("hazardous?"));
 	   		
	   	// 5. Put data in table
	   	CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
	   	casesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
	   	noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());
	   	contextsColumn.setCellValueFactory(cellData -> cellData.getValue().getContextsProperty());
	   	hazardousColumn.setCellValueFactory(cellData -> cellData.getValue().getHazardousProperty());
	   	
	    contextTable.setItems(mcsData);
	 	   
	}
	
}
