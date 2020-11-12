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
import javafx.stage.Modality;
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
	@FXML private Button toCTMButton, addTabButton;
	
	private ObservableList<Tab> tabs = FXCollections.observableArrayList();
	private ArrayList<String> tabNames = new ArrayList<String>();
	
	PmmTabPopUpController c;
	
	public PmmController() {

	}

	// Get Controller, all of CA from CSE DataStore
	public void selectController() {	
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡSelect Controllerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

		// CSE -> PMM
		// get data of selected controller
		Controller controller = components.curController;
//		controllerName.add(controller.getName());
		
		System.out.println("selected controller : " + controller.getName());
		System.out.println("total controller : " + controllerName);
			
		//for whole controllerName List
		if(controllerName.isEmpty()) {
			controllerName.add(controller.getName());
		}else {
			for(int i = 0; i < controllerName.size(); i++) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.addAll(controllerName);
				System.out.println("compare with whole controllerName List");
				if(arr.get(i).equals(controller.getName())) {
					//already has controller with same name
					System.out.println("No need to add new controller to controllerName list\n");
					continue;
				}else {
					//if there is no controller name matching with controllerNameList, add new controller
					System.out.println("Add new Controller : " + controller.getName());
				
					// Create new tab
//					if(!dataStore.getControllerName().isEmpty()) {
//					//if previously added controller exists, put it in array 
//					controllerName.addAll(dataStore.getControllerName());
//					}
					//add new controller to list
					System.out.println("No matching controller; add new controller to list");
					controllerName.add(controller.getName());

					System.out.println("controller after save: " + controllerName);
					System.out.println("controllerName size: " + controllerName.size() + ", tabsize: " + tabPane.getTabs().size());
				
					if(controllerName.size() > tabPane.getTabs().size()) {
						//need to create new tab
						System.out.println("Create new tab for new controller.\n");
						addTab(tabPane);
					}else {
						//use default tab(tab_1)
						System.out.println("No need to add new tab.\n");
					}
				}
			}
		}

		curIndex = controllerName.indexOf(controller.getName());
		System.out.println("index of selected controller : " + curIndex + "\n");	
		
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
			if(allCAs.isEmpty()) {
				allCAs.add(allCA);
			}else {
				for(int i = 0; i < allCAs.size(); i++) {
					ArrayList<String> arr = new ArrayList<String>();
					arr.addAll(allCAs.get(i));
					if(arr.equals(allCA)) {
						//if selected allCA is already in list for allCA, don't add to allCA list
						System.out.println("No need to add new CA into allCA list");
						continue;
					}else {
						System.out.println("Add allCA into AllCA list with index of " + curIndex);
						allCAs.add(allCA);
					}
				}
			}
			System.out.println(allCA + " : all CA");
			System.out.println(allCAs + " : list of all CA");
			
			if(curIndex == 0 && allCAs.get(curIndex).size() == 1) {
				tab_1.setText(controllerName.get(curIndex) + "-" + allCAs.get(curIndex).get(0));
			}
		}
			
		dataStore.setAllCA(allCAs);
		
		controllerList.getItems().add(controllerName.get(curIndex));
		CAList.getItems().addAll(allCAs.get(curIndex));
		
		if(allCAs.get(curIndex).size() > 1) {
			//if this tab is tab for controller with multiple CAs
			if(CAList.getSelectionModel().getSelectedItem() != allCAs.get(curIndex).get(0)) {
				//if this is not the first CA for first controller,
				addTab(tabPane);
			}
		}
	}
	
	@FXML
	public void showOutput() {
		// Save selected controller, ca in datastore
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡSHOW outputㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		
//		dataStore.setControllerName(controllerName);
		String curCA = CAList.getSelectionModel().getSelectedItem();
		CANameBar.setText(curCA);
		
		if(curIndex == 0 && allCAs.get(curIndex).size() > 1 && tab_1.getText() == null) {
			//if this tab is tab for first controller & there are more than one CA in first controller
			if(curCA.equals(allCAs.get(0).get(0))) {
				//only for the first controller
				setTabTitle(tab_1, controllerName.get(0), allCAs.get(0).get(0));
			}
		}else {
			setTabTitle(tab_1, controllerName.get(0), allCAs.get(0).get(0));
			Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
			if(selectedTab.getText() == null)
			setTabTitle(selectedTab, controllerList.getSelectionModel().getSelectedItem(), curCA);
		}
		
		System.out.println("[datastore]controllerName : " + controllerName);

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
	
	public void addTab(TabPane tabpane) {
		//add new tab to tabPane
		Tab newTab = new Tab();
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
        
        //setting tab name
      if(curIndex == 0 && allCAs.get(curIndex).size() > 1 && tab_1.getText() == null) {
			//if this tab is tab for first controller & there are more than one CA in first controller
			if(CAList.getSelectionModel().getSelectedItem().equals(allCAs.get(0).get(0))) {
				//only for the first controller
				setTabTitle(tab_1, dataStore.getControllerName().get(0), dataStore.getAllCA().get(0).get(0));
			}
		}else {
			setTabTitle(tab_1, dataStore.getControllerName().get(0), dataStore.getAllCA().get(0).get(0));
			Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
			if(selectedTab.getText() == null) {
				int index1 = dataStore.getControllerName().indexOf(controllerList.getSelectionModel().getSelectedItem());
				int index2 = dataStore.getAllCA().get(index1).indexOf(CAList.getSelectionModel().getSelectedItem());
				setTabTitle(selectedTab, dataStore.getControllerName().get(index1), dataStore.getAllCA().get(index1).get(index2));
			}
		}
        
        if( selectedOutputs != null) {
	  		if(!addFile.isVisible()) {
		  		try {
		  			FXMLLoader loader = new FXMLLoader();
		    		Parent parent = loader.load(getClass().getResource("popup/VariablePopUpView.fxml"));
		    		Scene scene = new Scene(parent);
		            
		    		valueStage.initModality(Modality.WINDOW_MODAL);
		    		valueStage.initOwner(mainApp.getPrimaryStage());
		    		valueStage.setTitle("Add Process Model variable");
		    		valueStage.setResizable(false);
		    		valueStage.show();	
					
			    	VariablePopUpController popup = loader.getController();
			    	popup.setStage(valueStage);
			    	dataStore.addValuelist(popup.value);
			    	if(popup.closeClicked() == true || popup.confirmClicked() == true) {
			    		valueStage.close();
			    	}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		          e.consume();
	  		}
        } else
        	System.out.println("Error: select output variable.");

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
		System.out.println("\n**********************");
		System.out.println("initialize!! ");
		// PMM, CSE Data Store
		dataStore = this.mainApp.models;
		components = this.mainApp.components;
	
		//controller's total count
		int controllerCnt = components.getControllers().size();
		System.out.println("total controller numbers : " + controllerCnt);
		
		// From Dashboard to PMM
		if(components.curController == null) { 
			// When through file open,
 
			if((!components.getControllers().isEmpty()) && dataStore.getControllerName().isEmpty()){
				// After working in CSE
				System.out.println("getting completed control structure's data.");
				
				// Move controller from CSE to VIEW
				int i = 0;
				for(Controller c : components.getControllers()) {
					controllerList.getItems().add(c.getName());
					System.out.println("add controller to controllerName list with index of " + i);
					controllerName.add(i, c.getName());
					i++;
				}
				
				// Get selected controller from list & Add ca in list
				CAList.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						try{
//							allCA.clear();
//							allCAs.clear();
							CAList.getItems().clear();
							
							System.out.println("whole controller : " + controllerName);
							int currentIndex = controllerName.indexOf(controllerList.getSelectionModel().getSelectedItem());
							for(int i = 0; i < components.getControllers().size(); i++) {
								System.out.println("controller " + i + " from CSE : " + components.getControllers().get(i).getName());
							}
							Controller controller = components.getControllers().get(currentIndex);
							System.out.println(controller.getName() + " : find selected controller in CSE DB\n");
							
							Map<Integer, Integer> controlActions = controller.getCA();
							System.out.println("get CA : " + controlActions);
							for(Integer ca : controlActions.keySet()) {
								System.out.println("if there is related CA");
								allCA.addAll(components.findControlAction(ca).getCA());
								System.out.println("add allCA connected to selected controller\n");
								if(allCAs.isEmpty()) {
									System.out.println("To add allCA to allCA list");
									allCAs.add(allCA);
									System.out.println("allCA : " + allCA);
								}else {
									for(int i = 0; i < allCAs.size(); i++) {
										System.out.println("To add allCA to not Empty allCA list");
										ArrayList<String> arr = new ArrayList<String>();
										arr.addAll(allCAs.get(i));
										if(arr.equals(allCA)) {
											//if selected allCA is already in list for allCA, don't add to allCA list
											System.out.println("No need to add new CA into allCA list\n");
											continue;
										}else {
											System.out.println("Add allCA into AllCA list with index of " + currentIndex);
											allCAs.add(allCA);
											System.out.println("AllCAs : " + allCAs + "\n");
										}
									}
								}
							}

							for(ArrayList<String> arr : allCAs) {
								//for allCA in allCAs
								if(allCAs.indexOf(arr) == currentIndex && !CAList.getItems().contains(arr)) {
									//add into CAList when item is not added before
									CAList.getItems().addAll(allCAs.get(currentIndex));
								}
								System.out.println("\nCAList items" + CAList.getItems() + "\n");
							}
						}catch(NullPointerException e) {
							e.getStackTrace();
						}
					}
				});
			}else {
			// while working data in PMM
			System.out.println("You have working data.");
			}
		} else { 
			// CSE -> PMM
			controllerName = dataStore.getControllerName();
			
			System.out.println("controllers in list : " + controllerName);
			System.out.println("*****curController:" + components.curController.getName());

			selectController();
		}

		// Data
		selectedFile = dataStore.getFilePath();
		selectedCAs = dataStore.getControlActionNames();
		selectedOutputs = dataStore.getOutputNames();
		allOutput = dataStore.getAllOutput();
		valuelist = dataStore.getValuelist();

		// View 
		outputList.getItems().addAll(allOutput);
		PM.setItems(valuelist);
		
		//tab data
//		for(int i = 0; i < tabPane.getTabs().size(); i++) {
//			tabPane.getTabs().get(i).setText(tabNames.get(i));
//			System.out.println("current tabs : " + tabPane.getTabs().get(i).getText());
//		}
		
		addTabButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Tab newTab = new Tab();
				tabPane.getTabs().add(newTab);
				AnchorPane newPane = new AnchorPane();
				ListView newListView = new ListView();
			
				newTab.setContent(newPane);
				newPane.getChildren().add(newListView);
			
				newPane.setTopAnchor(newListView, 0.0);
				newPane.setLeftAnchor(newListView, 0.0);
				newPane.setBottomAnchor(newListView, 0.0);
				newPane.setRightAnchor(newListView, 0.0);
			
//				newTab.setText(controllerList.getSelectionModel().getSelectedItem() + "-" + CAList.getSelectionModel().getSelectedItem());;
			}
		});			
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
	
	//set tab's title
	public void setTabTitle(Tab selectedTab, String controllerName, String caName) {
		selectedTab.setText(controllerName + "-" + caName);
		tabs.add(selectedTab);
		tabNames.add(selectedTab.getText());
		System.out.println("selected tab's name is : " + selectedTab.getText());
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
