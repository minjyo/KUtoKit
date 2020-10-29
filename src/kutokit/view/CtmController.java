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
	@FXML private TableColumn<CTM, String> contexts9Column = new TableColumn<>("contexts9");
	@FXML private TableColumn<CTM, String> contexts10Column = new TableColumn<>("contexts10");
	@FXML private TableColumn<CTM, String> contexts11Column = new TableColumn<>("contexts11");
	@FXML private TableColumn<CTM, String> contexts12Column = new TableColumn<>("contexts12");
	@FXML private TableColumn<CTM, String> contexts13Column = new TableColumn<>("contexts13");
	@FXML private TableColumn<CTM, String> contexts14Column = new TableColumn<>("contexts14");
	@FXML private TableColumn<CTM, String> contexts15Column = new TableColumn<>("contexts15");
	@FXML private TableColumn hazardousColumn;
	@FXML private TableColumn<CTM, Integer> noColumn = new TableColumn<>("no");
	
	private String[] no = new String[100];
	private String context[][] = new String[15][100];
	/*private String[] context1 = new String[100];
	private String[] context2 = new String[100];
	private String[] context3 = new String[100];
	private String[] context4 = new String[100];
	private String[] context5 = new String[100];
	private String[] context6 = new String[100];
	private String[] context7 = new String[100];
	private String[] context8 = new String[100];*/
	private String[] contextheader = new String[15];
	
	
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
			int j=0, k=0;

			//System.out.println(splits.length);
			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(splits[j].contains("HI_LOG_POWER")) {
					contextheader[k] = splits[j];
					if(k<15-1) k++;
				}
				for(int t=0;t<k;t++) {
					if(splits[j].contains(contextheader[t])) {
						if(context[t][i]==null) {
							context[t][i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context[t][i] += (" & \n" + splits[j].substring(index+1));
						}
					}
				}
				
				for(int x=0;x<8;x++) {
					for(int y=0;y<100;y++) {
						if(context[x][y]==null) {
							context[x][y] = "N/A";
						} 
					}
				}
					/*if(splits[j].contains("Trip_Out")) {
						if(context1[i]==null) {
							context1[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context1[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("Trip_Logic")) {
						if(context2[i]==null) {
							context2[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context2[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("Trip_Logic_State")) {
						if(context3[i]==null) {
							context3[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context3[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("PV")) {
						if(context4[i]==null) {
							splits[j]= splits[j].replace("f_HI_LOG_POWER_PV", "x");
							context4[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							splits[j]= splits[j].replace("f_HI_LOG_POWER_PV", "x");
							context4[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("Query")) {
						if(context5[i]==null) {
							context5[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context5[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("Mod_Err")) {
						if(context6[i]==null) {
							context6[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context6[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("Chan_Err")) {
						if(context7[i]==null) {
							context7[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context7[i] += (" & \n" + splits[j].substring(index+1));
						}
					} else if(splits[j].contains("PV_Err")) {
						if(context8[i]==null) {
							context8[i] = splits[j].substring(index+1);
						} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
							context8[i] += (" & \n" + splits[j].substring(index+1));
						}
					}*/
				j++;
			}

			
			/*if(context1[i]==null) {
				context1[i] = "N/A";
			} 
			if(context2[i]==null) {
				context2[i] = "N/A";
			} 
			if(context3[i]==null) {
				context3[i] = "N/A";
			}
			if(context4[i]==null) {
				context4[i] = "N/A";
			}
			if(context5[i]==null) {
				context5[i] = "N/A";
			}
			if(context6[i]==null) {
				context6[i] = "N/A";
			}
			if(context7[i]==null) {
				context7[i] = "N/A";
			}
			if(context8[i]==null) {
				context8[i] = "N/A";
			}*/
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
 	    contexts9Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts9"));
 	    contexts10Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts10"));
 	    contexts11Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts11"));
 	    contexts12Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts12"));
 	    contexts13Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts13"));
 	    contexts14Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts14"));
 	    contexts15Column.setCellValueFactory(new PropertyValueFactory<CTM, String>("contexts15"));
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
	    contexts9Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts10Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts11Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts12Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts13Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts14Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    contexts15Column.setCellFactory(TextFieldTableCell.forTableColumn());
	    
	    

		casesColumn.setVisible(false);
	}
	
	public void fillContextTable() {
	    // 3. Create Data list ex
		ObservableList<CTM> mcsData = FXCollections.observableArrayList();
		while(i < no.length) {
		//while(i < 1) {
			mcsData.add(new CTM("Trip signal", "Not provided\ncauses hazard", i+1, context[0][i], context[1][i], context[2][i], context[3][i], context[4][i], context[5][i], context[6][i], context[7][i], FXCollections.observableArrayList("O","X")));
			//mcsData.add(new CTM("", "", 1, "", "", "", "", "", "", "", "", FXCollections.observableArrayList("O","X")));
			i++;
		};
	    contextTable.setItems(mcsData);
	}
	
	@FXML
	public void onEditChange(TableColumn.CellEditEvent<CTM, String> productStringCellEditEvent) {
		CTM temp = contextTable.getSelectionModel().getSelectedItem();
		temp.setContext1(productStringCellEditEvent.getNewValue());
		
		//Todo :: @@@@@@@@Edit Value@@@@@@@@@@
		context[0][0]=productStringCellEditEvent.getNewValue();
		System.out.println(context[0][0]);
		
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