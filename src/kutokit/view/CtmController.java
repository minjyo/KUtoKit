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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	@FXML private TextField TextFieldCA;
	@FXML private TextField TextFieldCases;
	@FXML private TextField TextFieldContext1;
	@FXML private TextField TextFieldContext2;
	@FXML private TextField TextFieldContext3;
	@FXML private TextField TextFieldContext4;
	@FXML private TextField TextFieldContext5;
	@FXML private TextField TextFieldContext6;
	@FXML private TextField TextFieldContext7;
	@FXML private TextField TextFieldContext8;
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
	@FXML private TableColumn hazardousColumn;
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
	
	int i = 0;
	
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
	            
	            this.ParseMSC(temps);
	            
	            
	            this.MakeTable();
	            this.fillContextTable();

	            //bis.close();    
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}

	private void ParseMSC(String[] temps) {
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
			i++;
		}
		//System.out.println(Arrays.toString(f_HI_LOG_POWER_Trip_Out));
		
	}
	
	private void MakeTable() {
       
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
 	    hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous"));

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
	    /*ObservableList<CTM> mcsData = FXCollections.observableArrayList(
			new CTM("Trip signal", "Not provided\ncauses hazard", i+1, f_HI_LOG_POWER_Trip_Out[i], th_HI_LOG_POWER_Trip_Logic[i], th_HI_LOG_POWER_Trip_Logic_State[i], f_HI_LOG_POWER_PV[i], f_HI_LOG_POWER_APT_Query[i], f_HI_LOG_POWER_Mod_Err[i], f_HI_LOG_POWER_Chan_Err[i], f_HI_LOG_POWER_PV_Err[i++], FXCollections.observableArrayList("hi")),
			new CTM("Trip signal", "Not provided\ncauses hazard", i+1, f_HI_LOG_POWER_Trip_Out[i], th_HI_LOG_POWER_Trip_Logic[i], th_HI_LOG_POWER_Trip_Logic_State[i], f_HI_LOG_POWER_PV[i], f_HI_LOG_POWER_APT_Query[i], f_HI_LOG_POWER_Mod_Err[i], f_HI_LOG_POWER_Chan_Err[i], f_HI_LOG_POWER_PV_Err[i++], FXCollections.observableArrayList("hi")),
			new CTM("Trip signal", "Not provided\ncauses hazard", i+1, f_HI_LOG_POWER_Trip_Out[i], th_HI_LOG_POWER_Trip_Logic[i], th_HI_LOG_POWER_Trip_Logic_State[i], f_HI_LOG_POWER_PV[i], f_HI_LOG_POWER_APT_Query[i], f_HI_LOG_POWER_Mod_Err[i], f_HI_LOG_POWER_Chan_Err[i], f_HI_LOG_POWER_PV_Err[i++], FXCollections.observableArrayList("hi"))
		);*/
	    
	    contextTable.setEditable(true);
	    contexts1Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts2Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts3Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts4Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts5Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts6Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts7Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts8Column.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public void fillContextTable() {
	    // 3. Create Data list ex
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
		while(i < no.length) {
		//while(i < 1) {
			mcsData.add(new CTM("Trip signal", "Not provided\ncauses hazard", i+1, f_HI_LOG_POWER_Trip_Out[i], th_HI_LOG_POWER_Trip_Logic[i], th_HI_LOG_POWER_Trip_Logic_State[i], f_HI_LOG_POWER_PV[i], f_HI_LOG_POWER_APT_Query[i], f_HI_LOG_POWER_Mod_Err[i], f_HI_LOG_POWER_Chan_Err[i], f_HI_LOG_POWER_PV_Err[i], FXCollections.observableArrayList("O","X")));
			//mcsData.add(new CTM("", "", 1, "", "", "", "", "", "", "", "", FXCollections.observableArrayList("O","X")));
			i++;
		};
	    contextTable.setItems(mcsData);
	}
	
	@FXML
	public void onEditChange(TableColumn.CellEditEvent<CTM, String> productStringCellEditEvent) {
		CTM context = contextTable.getSelectionModel().getSelectedItem();
		context.setContext1(productStringCellEditEvent.getNewValue());
		
		//Todo :: @@@@@@@@Edit Value@@@@@@@@@@
		f_HI_LOG_POWER_Trip_Out[0]=productStringCellEditEvent.getNewValue();
		System.out.println(f_HI_LOG_POWER_Trip_Out[0]);
		
	}
	
	public ObservableList<CTM> getContextTableData() {
	       System.out.println(myTable.get(0));
	      return myTable;
	}
	
	@FXML
	public void addContext(ActionEvent actionEvent) {
		CTM newContext = new CTM(TextFieldCA.getText(), TextFieldCases.getText(), ++i, TextFieldContext1.getText(), TextFieldContext2.getText(), TextFieldContext3.getText(), TextFieldContext4.getText(), TextFieldContext5.getText(), TextFieldContext6.getText(), TextFieldContext7.getText(), TextFieldContext8.getText(), FXCollections.observableArrayList("O","X"));
		contextTable.getItems().add(newContext);
	}
	
	@FXML
	public void closeAddFile(ActionEvent actionEvent) {
		AddFile.getChildren().clear();
		MakeTable();
	}
	
}