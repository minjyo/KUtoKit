package kutokit.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCADataStore;

public class CTMController_2 implements Initializable{

	private MainApp mainApp;
	private CTMDataStore ctmDB;
	private File selectedFile;
	private ProcessModel pm;
	
	@FXML private ObservableList<TableView<CTM>> ctmTableList = FXCollections.observableArrayList();
	@FXML private ComboBox<String> controllerCB, controlActionCB;
	@FXML private Button addTabButton, addFileButton;
	@FXML private Label fileName;
	@FXML private TabPane tabPane;
	
	
	private static ObservableList<CTMDataStore> ctmDataStoreList = FXCollections.observableArrayList();
	ObservableList<CTMDataStore> totalData = FXCollections.observableArrayList();
	ObservableList<CTM> mcsData = FXCollections.observableArrayList();
	ObservableList<String> casesList = FXCollections.observableArrayList("Providing causes hazard", "Not providing\ncauses hazard", "Too soon, too late,\nout of order");
	ObservableList<String> hazardousList = FXCollections.observableArrayList("O", "X");
	ObservableList<String> controllerName = FXCollections.observableArrayList(pm.getControllerName());
	ObservableList<String> controlActionName = FXCollections.observableArrayList(pm.getControlActionName());
	ObservableList<String> selectedOutputName = FXCollections.observableArrayList(pm.getValuelist());
	ObservableList<String> contextList = FXCollections.observableArrayList();
	
//	private ObservableList<ComboBox<String>> controllerCBList = FXCollections.observableArrayList(controllerName);
//	private ObservableList<ComboBox<String>> controlActionCBList;
	private ObservableList<TableColumn<CTM, String>> casesColumnList = FXCollections.observableArrayList();
	private ObservableList<TableColumn<CTM, String>> noColumnList = FXCollections.observableArrayList();
	private ObservableList<TableColumn<CTM, String>> hazardousColumnList = FXCollections.observableArrayList();
	private ObservableList<Button> addFileButtonList;
	
	@SuppressWarnings("unchecked")
	private ComboBox<String> casesCB = new ComboBox<String>(casesList);
	@SuppressWarnings("unchecked")
	private ComboBox<String> hazardousCB = new ComboBox<String>(hazardousList);
	
	@SuppressWarnings("unchecked")
	private ObservableList<ComboBox<String>> casesCBList = FXCollections.observableArrayList(casesCB);
	@SuppressWarnings("unchecked")
	private ObservableList<ComboBox<String>> hazardousCBList = FXCollections.observableArrayList(hazardousCB);
	
	/*
	 * default constructor
	 */
	public CTMController_2() {
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
		ctmDataStoreList = mainApp.ctmDataStoreList;
		totalData = ctmDataStoreList;
		
		controllerName.addAll(pm.getControllerName());
		controlActionName.addAll(pm.getControlActionName());
		selectedOutputName.addAll(pm.getValuelist());
		controllerCB.getItems().addAll(controllerName);
//		controllerCBList.addAll(controllerCB);
		controlActionCB.getItems().addAll(controlActionName);
//		controlActionCBList.addAll(controlActionCB);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}
	@SuppressWarnings("unchecked")
	private void Initialize() {
		if(contextList.isEmpty()) {
			//first time getting selected valid output
			for(int a = 0; a < selectedOutputName.size(); a++) {
				contextList.add(selectedOutputName.get(a));
			}
		}else {
			//updating selected valid output
			for(int a = 0; a < selectedOutputName.size(); a++) {
				for(int b = 0; b < contextList.size(); b++) {
					//if selected valid output is already in the list, don't add it
					if(!contextList.get(b).equals(selectedOutputName.get(a))) {
						contextList.add(selectedOutputName.get(a));
					}else {
						continue;
					}
				}
			}
		}
		
		//adding new tab with add new tab button
		addTabButton.setOnAction(event -> {
			Tab newTab = new Tab("CT" + tabPane.getTabs().size() + 1);
			tabPane.setPrefWidth(1000.0);
			tabPane.setPrefHeight(730.0);
			tabPane.getTabs().add(newTab);
			newTab.setContent(setNewTabContent());
			newTab.setClosable(false);
		});
		
		addFileButton.setOnAction(event -> {
			AddFile();
		});
	}
	
	/*
	 * new AnchorPane in new Tab
	 */
	public AnchorPane setNewTabContent() {
		AnchorPane newAnchorPane = new AnchorPane();
//		newAnchorPane.
		ScrollPane newScrollPane = new ScrollPane();
		
		return newAnchorPane;
	}
	
	public TableView<CTM> setNewTable() {
		TableView<CTM> contextTable = new TableView<CTM>();
		
		contextTable.prefWidthProperty().bind(tabPane.widthProperty());
		contextTable.prefWidth(1000.0);
		contextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CTM, String> CAColumn = new TableColumn<CTM,String>("Control Action");
		TableColumn casesColumn = new TableColumn("cases");
		TableColumn<CTM, Integer> noColumn = new TableColumn<CTM,Integer>("No.");
		TableColumn hazardousColumn = new TableColumn("Hazardous?");

		CAColumn.setPrefWidth(100.0);
		casesColumn.setPrefWidth(100.0);
		noColumn.setPrefWidth(30.0);
		hazardousColumn.setPrefWidth(100.0);
		
		contextTable.prefWidthProperty().bind(tabPane.widthProperty());
		contextTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 4. Set table row 
		CAColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("controlAction"));
		casesColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("cases"));
		noColumn.setCellValueFactory(new PropertyValueFactory<CTM, Integer>("no"));
		hazardousColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>("hazardous"));

		CAColumn.setCellValueFactory(cellData -> cellData.getValue().getControlActionProperty());
		noColumn.setCellValueFactory(cellData -> cellData.getValue().getNoProperty().asObject());

		contextTable.setEditable(true);
	    
		contextTable.getColumns().addAll(CAColumn, casesColumn, noColumn);
	    
 		for(final int[] x= {0,};x[0]<contextList.size();x[0]++) {
 			TableColumn<CTM, String> contextColumn = new TableColumn<>(contextList.get(x[0]));
 			contextTable.getColumns().add(contextColumn);
 			contextColumn.setPrefWidth(80.0);
 			contextColumn.setCellValueFactory(new PropertyValueFactory<CTM, String>(contextList.get(x[0])));
 			int temp = x[0];
 			contextColumn.setCellValueFactory(cellData -> cellData.getValue().getContextProperty(temp));
 			contextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
 			contextColumn.setOnEditCommit(
 	            new EventHandler<CellEditEvent<CTM, String>>() {
 	                @Override
 	                public void handle(CellEditEvent<CTM, String> t) {
 	                    ((CTM) t.getTableView().getItems().get(
 	                        t.getTablePosition().getRow())
 	                        ).setContext(temp, t.getNewValue());
 	                   System.out.println((t.getTableView().getItems().get(t.getTablePosition().getRow()).getContext(temp)));
 	                }
 	            }
 	        );
 		}
 		contextTable.getColumns().add(hazardousColumn);
 		
 		return contextTable;
	}

	//open file chooser to get MCS File
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose file to Apply");
        fc.setInitialDirectory(new File(Info.directory));
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        try {
				ApplyFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	//Apply chosen MCS File
	public int ApplyFile() throws IOException {
		
		if(selectedFile != null) {
	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            
	            byte [] buffer = new byte[fis.available()];
	            String temp="";
	            while((fis.read(buffer)) != -1) {
	            	temp = new String(buffer);
	            }    
	            fis.close();
	           
	            String[] temps = new String[1000];
	            temps = temp.split("\n");
	            this.ParseMCS(temps);
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
		return 0;
	}
	
	private void ParseMCS(String[] temps) {
		String[][] context = new String[contextList.size()][temps.length];

		int i=0;
		while(i < temps.length) {
			String[] splits = temps[i].split("&");
			int j=0;
			int temp=-1;

			while(j < splits.length) {
				int index= splits[j].indexOf("=");
				if(index>=0) {
					for(int t=0;t<contextList.size();t++) { //header loop
						if(splits[j].contains(contextList.get(t))) {
							if(context[t][i]==null) {
								context[t][i] = splits[j].substring(index+1);
								if(context[t][i].substring(0,1).contains("=")) {
									context[t][i] = context[t][i].replace("= ", "");
								}
								if(splits[j].substring(0,index).contains("!")) {
									if(splits[j].contains("false")) context[t][i] = "true";
									else if(splits[j].contains("true")) context[t][i] = "false";
								}
								if(splits[j].contains("<=")){
									context[t][i] = splits[j].replace(contextList.get(t), "x");
									context[t][i] = context[t][i].replace("(A)", "");
								}
							} else if(!splits[j].contains("true") && !splits[j].contains("false")) {
								context[t][i] += (" & \n" + splits[j].substring(index+1));
							}
							temp = t;
							break;
						}
					}
				} else if(index < 0 && temp >= 0) {
					if(context[temp][i]==null || context[temp][i].contains("true") || context[temp][i].contains("false")) {
						if(splits[j].length()!=1) {
							context[temp][i]=splits[j];
						}
					}else if(!context[temp][i].isEmpty() && !context[temp][i].contains("<=")){
						context[temp][i] += (" & \n" +splits[j]);
					}
					temp = -1;
				}		
				j++;
			}
			i++;
		}
		
		for(int x=0;x<contextList.size();x++) {
			for(int y=0;y<temps.length;y++) {
				if(context[x][y]==null) {
					context[x][y] = "N/A";
				}
			}
		}
		
		for(int y=0;y<temps.length;y++) {
	        String[] contexts = new String[contextList.size()];
			for(int x=0;x<contextList.size();x++) {
				contexts[x] = context[x][y];
			}
			
//	   		ComboBox<String> comboBox1 = new ComboBox(casesCB);
//	   		comboBox1.setOnAction(event ->
//	   				ctmDB.get);
//	   		comboBox1.valueProperty().addListener(new ChangeListener<String>() {
//			      @Override
//			      public void changed(ObservableValue observable, String oldValue, String newValue) {
//			    	  
//			    	 totalData.get(curControllerNum).get.get(curCANum).setCasesValue(newValue);
//			      }
//			    });
			
//	   		ComboBox<String> comboBox2 = new ComboBox(hazardousOX);
//	   		comboBox2.valueProperty().addListener(new ChangeListener<String>() {
//			      @Override
//			      public void changed(ObservableValue observable, String oldValue, String newValue) {
//			    	 totalData.get(curControllerNum).get(curCANum).setHazardousValue(newValue);
//			      }
//			    });
//			
			totalData.get(0).getCTMTableList().add(
					new CTM(controllerName.toString(), controlActionName.toString(), casesCB, totalData.size() + 1, contexts, hazardousCB)
			);
		}
	}

	
}