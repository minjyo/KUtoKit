package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.cse.Components;
import kutokit.model.cse.Controller;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.pmm.XmlReader;
import kutokit.view.popup.VariablePopUpController;
import kutokit.view.popup.PmmTabPopUpController;

public class PmmController{

	//connect controller&control action to new tab
	private XmlReader reader;
	private MainApp mainApp;
	private File selectedFile;
	private Components components;
	
	private ProcessModel dataStore;
	private ArrayList<String> controllerName = new ArrayList<String>(); // Selected controller

	private ArrayList<String> allCA = new ArrayList<String>(); //all CA from selected controller action
	private ArrayList<String> selectedCA = new ArrayList<String>(); //selected CA from allCA
	private ArrayList<String> selectedOutput = new ArrayList<String>(); //selected output

	
	private ArrayList<ArrayList<String>> allCAs = new ArrayList<ArrayList<String>>(); //list for allCA
	private ArrayList<ArrayList<String>> selectedCAs = new ArrayList<ArrayList<String>>(); //list for selectedCA
	private ArrayList<ArrayList<String>> selectedOutputs = new ArrayList<ArrayList<String>>(); //list for selectedOutput
	
	private ObservableList<String> allOutput = FXCollections.observableArrayList(); //parsed output
	private ObservableList<String> valuelist = FXCollections.observableArrayList(); //parsed value list
	private ObservableList<String> list = FXCollections.observableArrayList(); 
	private ArrayList<String> valuelists = new ArrayList<String>(); // valuelist's data store

	private Stage valueStage = new Stage();
	private ContextMenu contextMenu = new ContextMenu();
	private MenuItem item1, item2;
	public String curOutput;
	public int curIndex; //selected controller's index
	
	@FXML private Label fileName, CANameBar;
	@FXML private Pane addFile;
	@FXML private ChoiceBox<String> controllerList, CAList;
	@FXML private ListView<String> outputList, PM;
	@FXML private TabPane tabPane;
	@FXML private Tab tab_1; // default tab
//	@FXML private AnchorPane newPane;
	@FXML private Button toCTMButton;
	
	private ObservableList<Tab> tabs = FXCollections.observableArrayList();
	PmmTabPopUpController c;
	
	public PmmController() {

	}

	// Get Controller, all of CA from CSE DataStore
	public void selectController() {	
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡSelect Controllerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

		// CSE -> PMM
		// get data of selected controller
		Controller controller = components.curController;
		if(controllerName.size() == 0) {
			controllerName.add(controller.getName());
		}
			
		//for whole controllerName List
		for(String s : controllerName) {
			System.out.println("compare with whole controllerName List");
			s = controller.getName();
			if(s.equals(controller.getName())) {
				//already has controller with same name
				continue;
			}else {
				//if there is no controller name matching with controllerNameList, add new controller
				System.out.println("Add new Controller : " + controller.getName());
				
				// Create new tab
				if(!dataStore.getControllerName().isEmpty()) {
					//if previously added controller exists, put it in array 
					controllerName.addAll(dataStore.getControllerName());
				}
				//add new controller to list
				controllerName.add(controller.getName());
				System.out.println("controller after save: " + controllerName);
				System.out.println("controllerName size: " + controllerName.size() + ", tabsize: " + tabPane.getTabs().size());
				
				if(controllerName.size() > tabPane.getTabs().size()) {
					//need to create new tab
					System.out.println("Create new tab for new controller.");
					addTab(tabPane);
				}else {
					//use default tab(tab_1)
					System.out.println("No need to add new tab.");
				}
			}
		}
		
		curIndex = controllerName.indexOf(controller.getName());
		
		//get CA from selected controller
		Map<Integer, Integer> controlActions = controller.getCA();
		System.out.println(controlActions);

		if(!dataStore.isEmpty(dataStore.getAllCA())) {
			//if allCA is not empty
			allCAs = dataStore.getAllCA();
			System.out.println("all CA size : " + allCAs.size());
		}else {
			System.out.println("allCA is empty");
		}
		for(Integer ca : controlActions.keySet()) {
			allCA.addAll(components.findControlAction(ca).getCA());
			allCAs.add(allCA);
			System.out.println(allCA + " : all CA");
		}
			
		dataStore.setAllCA(allCAs);
		
		controllerList.getItems().addAll(controllerName);
		CAList.getItems().addAll(allCAs.get(curIndex));

	}
	
	@FXML
	public void showOutput() {
		// Save selected controller, ca in datastore
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡSHOW outputㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		
		dataStore.setControllerName(controllerName);
		System.out.println("[datastore]controllerName:"+controllerName);
		String curCA = CAList.getSelectionModel().getSelectedItem();
		CANameBar.setText(curCA);
		tab_1.setText(controllerList.getSelectionModel().getSelectedItem() + "-" + curCA);

		if(!dataStore.isEmpty(dataStore.getControlActionNames())) {
			//get previously selected CA
			selectedCAs = dataStore.getControlActionNames();
			System.out.println(dataStore.getSize(selectedCAs));
			selectedCAs.get(dataStore.getSize(selectedCAs)).add(curCA);
		} else {
			//previously selected CA is empty
			System.out.println("previously selected CA is empty");
			selectedCA.add(CAList.getSelectionModel().getSelectedItem());
			selectedCAs.add(selectedCA);
		}
		
		for(ArrayList<String> list : selectedCAs) {
			System.out.println("selected CA : "+list);
		}
		
		dataStore.setControlActionNames(selectedCAs);
		addFile.setVisible(true);
		
	}

	@FXML
	public void addNewTab(MouseEvent e) {
		Tab newTab = new Tab();
		tabPane.getTabs().add(newTab);
		AnchorPane newPane = new AnchorPane();
		ListView newListView = new ListView();
		
		newPane.getChildren().add(newListView);
		
		newPane.setTopAnchor(newListView, 0.0);
		newPane.setLeftAnchor(newListView, 0.0);
		newPane.setBottomAnchor(newListView, 0.0);
		newPane.setRightAnchor(newListView, 0.0);
		
		newTab.setText(controllerList.getSelectionModel().getSelectedItem() + "-" + CAList.getSelectionModel().getSelectedItem());;
	}
	
	public void addTab(TabPane tabpane) {
		//set new tab's name as controller name - CA name
		Tab newTab = new Tab(controllerList.getSelectionModel().getSelectedItem() + "-" + CAList.getSelectionModel().getSelectedItem());
		
		//add new tab to tabPane
		tabpane.getTabs().add(newTab);
		
		AnchorPane newPane = new AnchorPane();
		ListView newListView = new ListView();
		
	
		//add new anchorPane in newTab
		newTab.setContent(newPane);
//		tabPane.getTabs().add(newTab);
		
		//set new ListView in new anchorPane
		newPane.getChildren().add(newListView);
		newPane.setTopAnchor(newListView, 0.0);
		newPane.setLeftAnchor(newListView, 0.0);
		newPane.setBottomAnchor(newListView, 0.0);
		newPane.setRightAnchor(newListView, 0.0);
	}

	@FXML
	public void selectPM(MouseEvent e) {
		// Add new value
        System.out.println("PM CLICK");		
        if( selectedOutputs != null) {
	  		FXMLLoader loader = new FXMLLoader();
	  		loader.setLocation(getClass().getResource("popup/VariablePopUpView.fxml"));
	  		Parent root;
	  		
	  		if(!addFile.isVisible()) {
		  		try {
					root = loader.load();
					Scene s = new Scene(root);
				
					valueStage.setScene(s);
					valueStage.show();
					
					valueStage.setOnHidden((new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	VariablePopUpController popup = loader.getController();
					    	dataStore.addValuelist(popup.value);
					    }
					  }));
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		          e.consume();
	  		}
        } else 	System.out.println("Error: select output variable.");

	}
	
	@FXML
	public void selectValue(MouseEvent e) {
		
		String target = PM.getSelectionModel().getSelectedItem();
		
		// Modify & delete existed values
		 if (e.getButton() == MouseButton.SECONDARY) {
			 	contextMenu.getItems().clear();
			 	contextMenu.hide();
			 	
				item1 = new MenuItem("Modify");
		        item1.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	modifyPopUp(target);
		            }
		        });
		        item2 = new MenuItem("Delete");
		        item2.setOnAction(new EventHandler<ActionEvent>() {
		 
		            @Override
		            public void handle(ActionEvent event) {
		            	dataStore.deleteValue(target);
		            }
		        });
		        
		        contextMenu.getItems().addAll(item1, item2);

		        PM.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		            @Override
		            public void handle(ContextMenuEvent event) {
		            	if(target != null) {
			                contextMenu.show(PM, event.getScreenX(), event.getScreenY());		            		
		            	}else {
			            	contextMenu.hide();
		            	}
	            		event.consume();
		            }
		        });

         }
	}
	
	@FXML
	public void openFile(MouseEvent e) {
		if(!valueStage.isShowing() && selectedOutputs != null) {
			addFile.setVisible(true);
			System.out.println("CA CLICK");
	        e.consume();
		} else {
			System.out.println("Error: select output variable.");
		}
	}
	
	@FXML
	public void addFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Get output variables");
        fc.setInitialDirectory(new File(Info.directory));
        // Set default directory
        
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
        	dataStore.setFilePath(selectedFile);
	        fileName.setText(selectedFile.getName());

	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
        }
    }
	
	@FXML
	public void applyFile() {
		//first, clear all items
		addFile.getChildren().clear();
		PM.getItems().clear();
		
		curIndex = controllerName.indexOf(controllerList.getSelectionModel().getSelectedItem());
		
		// Create XmlReader constructor
        reader = new XmlReader(selectedFile.getName());
        
	     // Get selected output
        System.out.println(curIndex + " : index of selected controller");
	    outputList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    list = outputList.getSelectionModel().getSelectedItems();

	    for(String data : list) {
	    	selectedOutput.add(data);
	    	selectedOutputs.add(selectedOutput);
	    }

		// Make process model
		if(selectedFile != null && !selectedOutputs.isEmpty()) { 
			System.out.println("make process model");
			System.out.println(selectedFile);
			this.makeModel(selectedOutputs);
		}  
		
		// Get output variables
		else if(selectedOutputs.isEmpty()){
			
			System.out.println("parse output variable");

			// VIEW
			List<String> outputs = reader.getOutputs();
			for(String data : outputs) {
				allOutput.add(data);
			}	
			outputList.setItems(allOutput);
			
			dataStore.setOutputNames(selectedOutputs);
			dataStore.setAllOutput(allOutput);

		}
		close();
	}
	// ERROR; other controller's show output is not created 
	
	@FXML
	public void close() {
		addFile.setVisible(false);
	}
	
	// Search & remove expressions from value
	public List<String> checkValue(String[] valueName, ArrayList<String> outputVariables) {

		String[] expressions = { "<", ">", "=", ":=", "&", "|", "\\+", ">=", "<=", ":" };
		String[] conditions = { "true", "false", "k_", "g_" , curOutput}; 
		List<String> values = new ArrayList<String>();
		List<String> result = new ArrayList<String>();

		String[] innerstr = new String[1];
		String arg; // plus
		int cnt = 0;
		int next= 0;
		int temp = 0;
		
		// 1. Check expressions in values
		while(cnt < valueName.length) {
			String str = String.valueOf(valueName[cnt]);
			if("null".equals(str)) break;
			str = str.replace("\n", "");
			str = str.trim();
			
			// Iteration for finding if string has expressions
			for(int j=0; j<expressions.length; ) {
				arg = expressions[j];	
				// System.out.println("current string: "+str);
				if(expressions[j].equals("\\+")) arg = "+";
				
				// Expression O
				if(str.contains(arg)) { 
					// System.out.println("expression : "+arg);
					next = str.indexOf(arg);
					innerstr[0] = str.substring(0, next);
					for(Object checked: checkValue(innerstr, outputVariables)) {
						values.add((String) checked);	
					}
					str = str.substring(next+1);
					j = 0;
					temp = 0;
					continue;
				} 
				// Expression X
				else {
					if( temp == 9 ) { 
						// System.out.println("break");
						values.add(str);
						temp = 0;
						break;
					}
					temp++;
					j++;
				}
			}
			cnt++;
		}
		
		// 2. Check conditions in values
		for(String str : values) {
			int close = 0;
			str = str.trim();
			
			if("".equals(str)) continue;
			else if ((conditions[0].equals(str)) || ((conditions[1]).equals(str)) || (conditions[4].equals(str))) continue;
			else if( conditions[2].equals(str.substring(0,2)) || conditions[3].equals(str.substring(0,2))) continue;
			else {
				for(String output : outputVariables) {
					if(str.equals(output)) {
						close = 1;
						break;
					}
				}
				if(close == 1) continue;
				else result.add(str);
			}
		}
		return result;
	}
	
	// Make process model 
	public void makeModel(ArrayList<ArrayList<String>> selectedOutput2) {
		
		String[] valueName = new String[50];
		NodeList l1;
		List<String> l2 = new ArrayList<String>();
		
		List<String> checkedl1 = new ArrayList<String>();
		List<String> checkedl2 = new ArrayList<String>();
		
		for(String output : selectedOutput2.get(curIndex)) {
			curOutput = output;
			String nodeType = curOutput.substring(0,1);

			l1 = reader.getNodeList(reader.getNode(curOutput),"");
			l2 = reader.getTransitionNodes(reader.getNode(curOutput));
				
			// SDT : L1+L2, TTS : L2
			// Get input variables from l1
			if( nodeType.equals("f") ) {
				for(int i = 0 ; i< l1.getLength(); i++) {
					String str = l1.item(i).getAttributes().getNamedItem("value").getTextContent();
					valueName[i] = str;
				}
				checkedl1 = checkValue(valueName, selectedOutput2.get(curIndex));
				for(Object value : checkedl1) {
					this.valuelist.add(value.toString());
				}	 
			}
			
			// Get transition variables from l2
			for(int i = 0 ; i< l2.size(); i++) {
				valueName[i] = l2.get(i);
			}
			checkedl2 = checkValue(valueName, selectedOutput2.get(curIndex));
			for(Object value : checkedl2) {
				this.valuelist.add(value.toString());
			}
			
		}		
		
		// Remove redundant variables between l1 and l2
		TreeSet tree = new TreeSet();
		for(String value: valuelist) {
			tree.add(value);
		}       
		
		valuelist.clear();
		
		Iterator it = tree.iterator();
		while ( it.hasNext() ) {
			valuelist.add(it.next().toString());
		}
		
//		dataStore.setValuelist(valuelist);
//		PM.setItems(valuelist);		
		
		System.out.println("[dataStore] controller name: "+dataStore.getControllerName());
		for(ArrayList<String> list: dataStore.getControlActionNames()) {
			System.out.println("[dataStore] selected ca: "+list);

		}
		System.out.println();
		
		for(ArrayList<String> list: dataStore.getOutputNames()) {
			System.out.println("[dataStore] selected output: "+list);
		}
		System.out.println();
		
		for(String list: dataStore.getValuelist()) {
			System.out.println("[dataStore] value list: "+list);
		}
		System.out.println();
	}

	private void initialize() {
		System.out.println("initialize!! ");
		// PMM, CSE Data Store
		dataStore = this.mainApp.models;
		components = this.mainApp.components;
	
		//controller's total count
		int controllerCnt = components.getControllers().size();

		// From Dashboard to PMM
		if(components.curController == null) { 
			// When through file open,
 
			if((!components.getControllers().isEmpty()) && dataStore.getControllerName().isEmpty()){
				// After working in CSE
				System.out.println("getting completed control structure's data.");
				
				// Move controller from CSE to VIEW
				for(Controller c : components.getControllers()) {
					controllerList.getItems().add(c.getName());
				}
				
				// Get selected controller from list & Add ca in list
				CAList.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						try{
							controllerName.clear();
							allCA.clear();
							allCAs.clear();
							CAList.getItems().clear();
							
							Controller controller = components.findController(controllerList.getSelectionModel().getSelectedItem());
							controllerName.add(controller.getName());
							
							Map<Integer, Integer> controlActions = controller.getCA();
							for(Integer ca : controlActions.keySet()) {
								allCA.addAll(components.findControlAction(ca).getCA());
							}
							allCAs.add(allCA);
							for(ArrayList<String> allCA : allCAs) {
								CAList.getItems().addAll(allCA);
							}
						}catch(NullPointerException e) {
							e.getStackTrace();
						}
					}
				});
			}
			else {
				// while working data in PMM
				System.out.println("You have working data.");
			}
		} else { 
			// CSE -> PMM
			System.out.println("*****curController:"+components.curController.getName());
			selectController();
		}

		// Data
				selectedFile = dataStore.getFilePath();
				selectedCAs = dataStore.getControlActionNames();
				selectedOutputs = dataStore  .getOutputNames();
				allOutput = dataStore.getAllOutput();
				valuelist = dataStore.getValuelist();

				// View 
				outputList.getItems().addAll(allOutput);
				PM.setItems(valuelist);
				
	}

	
	// Show value edit popup
	public void modifyPopUp(String oldvalue) {
		
		if( allOutput != null) {
	  		FXMLLoader loader = new FXMLLoader();
	  		loader.setLocation(getClass().getResource("popup/VariablePopUpView.fxml"));
	  		Parent root;
	  		
	  		if(!addFile.isVisible()) {
		  		try {
					root = loader.load();
					Scene s = new Scene(root);
				
					valueStage.setScene(s);
					valueStage.show();
					
					valueStage.setOnHidden((new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	VariablePopUpController popup = loader.getController();
					    	dataStore.modifyValue(oldvalue, popup.value);
					    }
					  }));
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	  		} 
        } else 	System.out.println("Error: select output variable.");
    	PM.setItems(valuelist);

	}

	public void savePM(String name, List list) {
		
	}
	
	@FXML
	public void toContextModel(MouseEvent e) {
		//get data from selected tab, move to ctm
		Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
		selectedTab.getContent();
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.initialize();
	}
}
