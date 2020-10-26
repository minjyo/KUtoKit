package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.MainApp;
import kutokit.model.CTM;

public class CtmController {

	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<CTM> myTable;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	//!!지금부터시작!!
	@FXML private TableView<CTM> contextTable;
	@FXML private TableColumn<CTM, String> CAColumn = new TableColumn<>("CA"); 
	@FXML private TableColumn<CTM, String> casesColumn = new TableColumn<>("cases");
	@FXML private TableColumn<CTM, String> contexts1Column = new TableColumn<>("contexts1");
	@FXML private TableColumn<CTM, String> contexts2Column = new TableColumn<>("contexts2");
	@FXML private TableColumn<CTM, String> contexts3Column = new TableColumn<>("contexts3");
	@FXML private TableColumn<CTM, String> contexts4Column = new TableColumn<>("contexts4");
	@FXML private TableColumn<CTM, String> contexts5Column = new TableColumn<>("contexts5");
	@FXML private TableColumn<CTM, String> contexts6Column = new TableColumn<>("contexts6");
	@FXML private TableColumn<CTM, String> contexts7Column = new TableColumn<>("contexts7");
	@FXML private TableColumn<CTM, String> contexts8Column = new TableColumn<>("contexts8");
	@FXML private TableColumn<CTM, String> hazardousColumn = new TableColumn<>("hazardous");
	@FXML private TableColumn<CTM, Integer> noColumn = new TableColumn<>("no");

	private String[] no = new String[100];
	private String[] f_HI_LOG_POWER_Trip_Out = new String[100];
	private String[] th_HI_LOG_POWER_Trip_Logic = new String[100];
	private String[] th_HI_LOG_POWER_Trip_Logic_State = new String[100];
	private String[] f_HI_LOG_POWER_PV = new String[100];
	private String[] f_HI_LOG_POWER_APT_Query = new String[100];
	private String[] f_HI_LOG_POWER_Mod_Err = new String[100];
	private String[] f_HI_LOG_POWER_Chan_Err = new String[100];
	private String[] f_HI_LOG_POWER_PV_Err = new String[100];
	
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
        fc.setInitialDirectory(new File("/Users/jerry/dearyeon/KUtoKit/"));
        // 확장자 제한
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile); 
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
	            //BufferedInputStream bis = new BufferedInputStream(fis);
	            
	            byte [] buffer = new byte[fis.available()];
	            String temp="";
	            while((fis.read(buffer)) != -1) {
	            	temp = new String(buffer);
	            }    
	            fis.close();
	           
	            //2. Add Parsing File
	            String[] temps = new String[1000];
	            temps = temp.split("\n");
	            //System.out.println(temps[0]);
	            //System.out.println(temps[1]);
	            
	            this.ParseMCS(temps);
	            
	            
	            this.MakeTable();

	            //bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private void ParseMCS(String[] temps) {
		//MSC ex 
//		detect_term≤0.1sec Λ 	f_HI_LOG_POWER_Trip_Out=true
//		detect_length≥1m Λ		f_HI_LOG_POWER_PV_Err=true
//		sensor_error=false Λ 	th_HI_LOG_POWER_Trip_Logic=false
//	malfunc_check_clear=true Λ 	th_HI_LOG_POWER_Trip_Logic_state=Waiting at t
//		path_check=true Λ 		th_HI_LOG_POWER_Trip_Logic_state=Waiting at t=1
//		gps_one=true
		int i=0;
		while(i < temps.length) {
			no[i] = temps[i].substring(0, 1);
			String[] splits = temps[i].split("&");
			int j=0;
			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(splits[j].contains("Trip_Out")) {
					if(f_HI_LOG_POWER_Trip_Out[i]==null) {
						f_HI_LOG_POWER_Trip_Out[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						f_HI_LOG_POWER_Trip_Out[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("Trip_Logic")) {
					if(th_HI_LOG_POWER_Trip_Logic[i]==null) {
						th_HI_LOG_POWER_Trip_Logic[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						th_HI_LOG_POWER_Trip_Logic[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("Trip_Logic_State")) {
					if(th_HI_LOG_POWER_Trip_Logic_State[i]==null) {
						th_HI_LOG_POWER_Trip_Logic_State[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						th_HI_LOG_POWER_Trip_Logic_State[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("PV")) {
					if(f_HI_LOG_POWER_PV[i]==null) {
						splits[j]= splits[j].replace("f_HI_LOG_POWER_PV", "x");
						f_HI_LOG_POWER_PV[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						splits[j]= splits[j].replace("f_HI_LOG_POWER_PV", "x");
						f_HI_LOG_POWER_PV[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("Query")) {
					if(f_HI_LOG_POWER_APT_Query[i]==null) {
						f_HI_LOG_POWER_APT_Query[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						f_HI_LOG_POWER_APT_Query[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("Mod_Err")) {
					if(f_HI_LOG_POWER_Mod_Err[i]==null) {
						f_HI_LOG_POWER_Mod_Err[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						f_HI_LOG_POWER_Mod_Err[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("Chan_Err")) {
					if(f_HI_LOG_POWER_Chan_Err[i]==null) {
						f_HI_LOG_POWER_Chan_Err[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						f_HI_LOG_POWER_Chan_Err[i] += (" & \n" + splits[j].substring(index+1));
					}
				} else if(splits[j].contains("PV_Err")) {
					if(f_HI_LOG_POWER_PV_Err[i]==null) {
						f_HI_LOG_POWER_PV_Err[i] = splits[j].substring(index+1);
					} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
						f_HI_LOG_POWER_PV_Err[i] += (" & \n" + splits[j].substring(index+1));
					}
				}
				j++;
			}

			
			if(f_HI_LOG_POWER_Trip_Out[i]==null) {
				f_HI_LOG_POWER_Trip_Out[i] = "N/A";
			} 
			if(th_HI_LOG_POWER_Trip_Logic[i]==null) {
				th_HI_LOG_POWER_Trip_Logic[i] = "N/A";
			} 
			if(th_HI_LOG_POWER_Trip_Logic_State[i]==null) {
				th_HI_LOG_POWER_Trip_Logic_State[i] = "N/A";
			}
			if(f_HI_LOG_POWER_PV[i]==null) {
				f_HI_LOG_POWER_PV[i] = "N/A";
			}
			if(f_HI_LOG_POWER_APT_Query[i]==null) {
				f_HI_LOG_POWER_APT_Query[i] = "N/A";
			}
			if(f_HI_LOG_POWER_Mod_Err[i]==null) {
				f_HI_LOG_POWER_Mod_Err[i] = "N/A";
			}
			if(f_HI_LOG_POWER_Chan_Err[i]==null) {
				f_HI_LOG_POWER_Chan_Err[i] = "N/A";
			}
			if(f_HI_LOG_POWER_PV_Err[i]==null) {
				f_HI_LOG_POWER_PV_Err[i] = "N/A";
			}
				/*if(splits.length > 2) { // a ≤ x ≤ b
				}else { // x ≤ a 
					contexts[i] = "x <= " +  splits[1];
				}*/
			/*else if(temps[i].contains("≥")) {
				String[] splits = temps[i].split("≥");
				contexts[i] = "x >= " +  splits[1];
			}else if(temps[i].contains("=")) { // x = true or false
				String[] splits = temps[i].split("=");
				contexts[i] = splits[1];
			}else {
				contexts[i] = "N/A";
			}*/
			i++;
		}
		//System.out.println(Arrays.toString(f_HI_LOG_POWER_Trip_Out));
		
	}
	
	private void MakeTable() {
		
		// 3. Create Data list ex
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
		
		int i=0;
		while(i < no.length) {
			mcsData.add(new CTM("Trip signal", "Not provided\ncauses hazard", i+1, f_HI_LOG_POWER_Trip_Out[i], th_HI_LOG_POWER_Trip_Logic[i], th_HI_LOG_POWER_Trip_Logic_State[i], f_HI_LOG_POWER_PV[i], f_HI_LOG_POWER_APT_Query[i], f_HI_LOG_POWER_Mod_Err[i], f_HI_LOG_POWER_Chan_Err[i], f_HI_LOG_POWER_PV_Err[i], FXCollections.observableArrayList("hihihi","hihihi1")));
			i++;
		};
		/*mcsData = FXCollections.observableArrayList(
			new CTM("Action", "case", i, f_HI_LOG_POWER_Trip_Out[i], f_HI_LOG_POWER_Chan_Err[i], FXCollections.observableArrayList("hihihi","hihihi1")),
			new CTM("Action", "case", i, "ct12", "ct2", FXCollections.observableArrayList("hihihi")),
			new CTM("Action", "case", i, "ct13", "ct2", FXCollections.observableArrayList("hihihi")),
			new CTM("Action", "case", i, "ct14", "ct2", FXCollections.observableArrayList("hihihi")),
			new CTM("Action", "case", i, "ct15", "ct2", FXCollections.observableArrayList("hihihi"))
		);*/
       
        // 4. Set table row 
        CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
   	 	casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
 	 	noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
 	    contexts1Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts1"));
 	    contexts2Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts2"));
 	    contexts3Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts3"));
 	    contexts4Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts4"));
 	    contexts5Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts5"));
 	    contexts6Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts6"));
 	    contexts7Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts7"));
 	    contexts8Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts8"));
 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous?"));
		
		
 	    //test1.setCellValueFactory(new PropertyValueFactory<CTM, String>("test1"));
 	    //test2.setCellValueFactory(new PropertyValueFactory<CTM, String>("test2"));
 	    //contextsColumn.getColumns().addAll(test1,test2);
 	    
	   	// 5. Put data in table
	   	/*CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
	   	casesColumn.setCellValueFactory(cellData -> cellData.getValue().getCasesProperty());
	   	noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());
	   	contextsColumn.setCellValueFactory(cellData -> cellData.getValue().getContextsProperty());
	   	hazardousColumn.setCellValueFactory(cellData -> cellData.getValue().getHazardousProperty());*/
	   	//hazardousColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Friends", "Family", "Work Contacts"));
		
		//contextTable.getColumns().addAll(CAColumn,casesColumn);
	    contextTable.setItems(mcsData);
	 	   
	}
	
	public ObservableList<CTM> getContextTableData() {

	       System.out.println(myTable.get(0));
	      return myTable;
	   }
}