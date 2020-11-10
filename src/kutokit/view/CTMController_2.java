package kutokit.view;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.ctm.CTM;
import kutokit.model.pmm.ProcessModel;

public class CTMController_2 implements Initializable{
	private MainApp mainApp;
	private File selectedFile;
	private ProcessModel pm;
	
	@FXML ComboBox<String> controllerCB, controlActionCB;
	@FXML Button addFileButton, addTabButton;
	@FXML Tab firstTab;
	@FXML TabPane tabPane;
	@FXML TableView<CTM> ctmTableView;
	TableColumn casesColumn, noColumn, hazardousColumn;
	
	private ObservableList<String> cases = FXCollections.observableArrayList(
			"Not providing\ncauses hazard",
			"Providing causes hazard",
			"Too early, too late,\nout of order",
			"Stopped too soon,\napplied too long");
	private ObservableList<String> hazardous = FXCollections.observableArrayList("O", "X");
	
	ComboBox<String> casesCB = new ComboBox<String>(cases);
	ComboBox<String> hazardousCB = new ComboBox<String>(hazardous);
	private ObservableList<String> contextList = FXCollections.observableArrayList();
	
	ObservableList<TableView<CTM>> totalTableView = FXCollections.observableArrayList();
	ObservableList<ComboBox> casesList = FXCollections.observableArrayList();
	ObservableList<ComboBox> hazardousList = FXCollections.observableArrayList();
	
	/*
	 * default constructor
	 */
	public CTMController_2() {
	}

	//set MainApp
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controllerCB.getItems().addAll(pm.getControllerName());
		controlActionCB.getItems().addAll(pm.getControlActionName());
		contextList = pm.getValuelist();
		totalTableView.add(ctmTableView);
		
		//set first first Tab name
		firstTab.setText(pm.getControllerName() + "-" + pm.getControlActionName());
		
		//if tab button is clicked, add new tab
		addTabButton.setOnAction(event -> {
			if(!controllerCB.getSelectionModel().getSelectedItem().isEmpty() && !controlActionCB.getSelectionModel().getSelectedItem().isEmpty()) {
				//if user selected controller & control action, add new tab
				addNewTab();
			}else {
				System.out.println("no selected controller & control action");
			}
		});
		
		//if File Button is clicked, add new File with file chooser
		addFileButton.setOnAction(event -> AddFile());
		
	}

	/*
	 * add new tab
	 */
	@FXML
	public void addNewTab() {
		//create new tab
		Tab newTab = new Tab();
		//create new scroll pane for new tab
		ScrollPane scrollPane = new ScrollPane();
		//create new tableView for new scroll pane
		TableView<CTM> newTableView = new TableView<CTM>();
		//create new table column for new table view
		TableColumn newCasesColumn = null, newNoColumn = null, newHazardousColumn = null;
		newCasesColumn.setPrefWidth(154);
		newCasesColumn.setResizable(false);
		newCasesColumn.setEditable(true);
		newNoColumn.setPrefWidth(42);
		newNoColumn.setResizable(false);
		newNoColumn.setEditable(true);
		newHazardousColumn.setPrefWidth(107);
		newHazardousColumn.setResizable(false);
		newHazardousColumn.setEditable(true);

		//set new tab's name
		newTab.setText(controllerCB.getSelectionModel().getSelectedItem() + "-" + controlActionCB.getSelectionModel().getSelectedItem());
		//add new tab into tabPane
		tabPane.getTabs().add(newTab);
		//set scrollPane in new tab
		newTab.setContent(scrollPane);
		//set new tableView in new scroll pane
		scrollPane.setContent(newTableView);
		//set new table columns in new table view
		newTableView.getColumns().add(newCasesColumn);
		newTableView.getColumns().add(newNoColumn);
		newTableView.getColumns().add(newNoColumn);
		
	}
	//open file chooser to get MCS File
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        
        //file chooser setting
        fc.setTitle("Choose file to Apply");
        fc.setInitialDirectory(new File(Info.directory));
        
        //only get .txt file format(MCS File)
        ExtensionFilter txtType = new ExtensionFilter("MCS file (*.txt)", "*.txt");
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
			
	   		ComboBox<String> comboBox1 = new ComboBox(cases);
//	   		comboBox1.setOnAction(event ->
//	   				ctmDB.get);
//	   		comboBox1.valueProperty().addListener(new ChangeListener<String>() {
//			      @Override
//			      public void changed(ObservableValue observable, String oldValue, String newValue) {
//			    	  
//			    	 totalData.get(curControllerNum).get.get(curCANum).setCasesValue(newValue);
//			      }
//			    });
			
	   		ComboBox<String> comboBox2 = new ComboBox(hazardous);
//	   		comboBox2.valueProperty().addListener(new ChangeListener<String>() {
//			      @Override
//			      public void changed(ObservableValue observable, String oldValue, String newValue) {
//			    	 totalData.get(curControllerNum).get(curCANum).setHazardousValue(newValue);
//			      }
//			    });
//			
			totalData.get(curControllerNum).getCTMTableList().add(
					new CTM(controllerName.get(curControllerNum), controlActionNames.get(curCANum),comboBox1,totalData.get(curControllerNum).tableSize+1,contexts,comboBox2)
			);
		}
	}
}