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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.fxml.FXML;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.control.ChoiceBox;
	import javafx.scene.control.ContextMenu;
	import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
	import javafx.scene.control.MenuItem;
	import javafx.scene.control.SelectionMode;
	import javafx.scene.control.Tab;
	import javafx.scene.control.TabPane;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Alert.AlertType;
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
	import kutokit.model.cse.ControlAction;
	import kutokit.model.cse.Controller;
	import kutokit.model.pmm.PmmDataStore;
	import kutokit.model.pmm.ProcessModel;
	import kutokit.model.pmm.XmlReader;
import kutokit.view.popup.SelectGroupFODController;
import kutokit.view.popup.VariablePopUpController;
	
public class PmmController {
	// connect controller&control action to new tab
	private XmlReader xmlReader;
	private MainApp mainApp;
	private File selectedFile;
	private Components components;
	private PmmDataStore pmmDB;
	
	private Stage valueStage = new Stage();
	private ContextMenu rightClickMenu = new ContextMenu();
	private MenuItem modifyMenu, deleteMenu;
	
	@FXML
	private Label fileName;
	@FXML
	private Pane addFile;
	@FXML
	private ChoiceBox<String> controllerList, CAList;
	@FXML
	private ListView<String> outputList;
	@FXML
	private TabPane tabPane;
	@FXML
	private Button addTabButton;
	
	//to control each listview for each controller
	ObservableList<ListView<String>> listViewList = FXCollections.observableArrayList();
	
	public PmmController() {
	
	}
	
	// Get Controller, all of CA from CSE DataStore
	public void selectController() {
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡSelect Controllerㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
	
		// CSE -> PMM
		// get data of selected controller & fix
		Controller controller = components.curController;
		controllerList.getItems().add(controller.getName());
		controllerList.setValue(controller.getName());
		controllerList.setDisable(true);
		
		tabPane.getTabs().clear();
		
		//set CAList item with selected controller
		CAList.getItems().clear();
	    for(ControlAction c : components.getControlActions()){
	    	if(c.getController().getName() == controllerList.getValue()){
	    		CAList.getItems().addAll(c.getCA());
	       }
	    }
	    
	    //show tab for controller
	    showControllerTab(controller.getName());
	
		System.out.println("selected controller : " + controller.getName());
	}

	private void showControllerTab(String controller) {
		// TODO Auto-generated method stub
		//to show tab for selected controller
		for(ProcessModel p : pmmDB.getProcessModel()) {
			if(p.getControllerName().equals(controller))
				addTab(p);
		}
	}
	
	public void addTab(ProcessModel p) {
		// add new tab to tabPane
		Tab newTab = new Tab();
		newTab.setText(p.getControlActionName());
		tabPane.getTabs().add(newTab);
	
		AnchorPane newPane = new AnchorPane();
		ListView newListView = new ListView();
	
		// add new anchorPane in newTab
		newTab.setContent(newPane);
	
		// set new ListView in new anchorPane
		newPane.getChildren().add(newListView);
		newPane.setTopAnchor(newListView, 0.0);
		newPane.setLeftAnchor(newListView, 0.0);
		newPane.setBottomAnchor(newListView, 0.0);
		newPane.setRightAnchor(newListView, 0.0);
		
		//add value list into list view
		newListView.getItems().addAll(p.getValuelist());
		listViewList.add(newListView);
	}

	@FXML
	public void showOutput() {
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡSHOW outputㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		addFile.setVisible(true);
	}
	

	@FXML
	public void addFile() {
		//file chooser
		FileChooser fc = new FileChooser();
		fc.setTitle("Get output variables");
		fc.setInitialDirectory(new File(Info.directory));
		// Set default directory
	
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fc.getExtensionFilters().add(extFilter);
	
		selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
//			dataStore.setFilePath(selectedFile);
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
	public void close() {
		//cancel button
		addFile.setVisible(false);
	}
	
	@FXML
	public void applyFile() {
		// clear all items
		addFile.getChildren().clear();
		outputList.getItems().clear();
	
		// Create XmlReader constructor
		xmlReader = new XmlReader(selectedFile.getName());
		ArrayList<String> showGroupNodesItems = new ArrayList<String>();
		
		//show popup to select group FODs from NuSRS file
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("popup/SelectGroupFODView.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			Stage selectGroupStage = new Stage();

			selectGroupStage.setTitle("Select output variable groups");
			selectGroupStage.setResizable(false);
			selectGroupStage.show();
			
			selectGroupStage.setScene(scene);
			selectGroupStage.setOnHidden((new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					SelectGroupFODController controller = loader.getController();
					controller.setRootFOD(xmlReader.getRootFod().toString());
					String rootFodText = xmlReader.getRootFod().toString();
					for(Node fod: xmlReader.showValidFods()) {
						showGroupNodesItems.add(fod.getAttributes().getNamedItem("name").toString());
					}
					controller.setGroupItems(showGroupNodesItems);
					if(controller.canceled == true) {
						selectGroupStage.close();
					}else if(controller.confirmed == true) {
						outputList.getItems().addAll(controller.selectedItems());
					}
				}
			}));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		
		
		
		
		ObservableList<String> outputlist = FXCollections.observableArrayList();
		// VIEW
		List<String> outputs = xmlReader.getOutputs();

		for (String data : outputs) {
			outputlist.add(data);
		}
		outputList.setItems(outputlist);
		pmmDB.setOutputList(outputlist);
		
		makeModel(outputlist);
	}
			
	@FXML
    public void addToProcessModel() {
		//add selected value from output list to value list
	    outputList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    
	    
	    ProcessModel PM = new ProcessModel();
	    for(ProcessModel pm : pmmDB.getProcessModel()){
	        if(pm.getControlActionName().equals(CAList.getValue()) && pm.getControllerName().equals(controllerList.getValue())){
	           PM = pm;
	        }
	     }
	    
	    ArrayList<String> selectedOutputs = new ArrayList<String>();
	//    ArrayList<String> newValues = new ArrayList<String>();
	    
	    if (selectedFile != null && !selectedOutputs.isEmpty()) {
	         ListView<String> lv = new ListView<String>();
	        
	         for(Tab tab : tabPane.getTabs()){
	            if(tab.getText().equals(CAList.getValue())){
	               lv = listViewList.get(tabPane.getTabs().indexOf(tab));
	            }
	         }
	         //for selectedOutputs, add related input datas in db
	         for(String selectedOutput : selectedOutputs){
	            int index = outputList.getItems().indexOf(selectedOutput);
	            System.out.println(index);
	            for(String dbInput : pmmDB.getInputList().get(index)){
	               boolean e = true;
	               for(String pms : PM.getValuelist()){
	                  if(pms.equals(dbInput)){
	                     e = false;
	                  }
	               }
	               if(e){
	                  PM.getValuelist().add(dbInput);
	                  lv.getItems().add(dbInput);
	               }
	            }
	         }
	         //add selected outputs in db
	         PM.setSelectedOutputs(selectedOutputs);
      	}else if (selectedOutputs.isEmpty()) {
	         // No selected Outputs, show alert
	         System.out.println("output variable");
	         Alert alert = new Alert(AlertType.WARNING);
	         alert.setTitle("Warning");
	         alert.setHeaderText("No selected outputs");
	         alert.setContentText("You have to select outputs first");
	
	         alert.showAndWait();
      	}
    }
	
	// Make process model
	public void makeModel(ObservableList<String> outputlist) {
	
		ArrayList<String> valueName = new ArrayList<String>();
		NodeList directlyConnectedNode;
		List<String> transitionNodes = new ArrayList<String>();
	
		List<String> directlyConnectedNodeList = new ArrayList<String>();
		List<String> transitionNodeList = new ArrayList<String>();
		
		ArrayList<String> curList = new ArrayList<String>();
	
		for (String output : outputlist) {
			String nodeType = output.substring(0, 1);
	
			//each node
			directlyConnectedNode = xmlReader.getNodeList(xmlReader.getNode(output), "");
			//related node
			transitionNodes = xmlReader.getTransitionNodes(xmlReader.getNode(output));
	
			// SDT : L1+L2, TTS : L2
			// Get input variables from 
			if (nodeType.equals("f")) {
				//SDT node
				for (int i = 0; i < directlyConnectedNode.getLength(); i++) {
					String str = directlyConnectedNode.item(i).getAttributes().getNamedItem("value").getTextContent();
					valueName.add(str);
				}
				directlyConnectedNodeList = checkValue(valueName, outputlist);
				for (Object value : directlyConnectedNodeList) {
					curList.add(value.toString());
				}
			}
	
			// Get transition variables from l2
			//FSM, TTS node
			for (int i = 0; i < transitionNodes.size(); i++) {
				valueName.add(transitionNodes.get(i));
			}
			transitionNodeList = checkValue(valueName, outputlist);
			for (Object value : transitionNodeList) {
				curList.add(value.toString());
			}
	
			// Remove redundant variables between l1 and l2
			TreeSet tree = new TreeSet();
			for (String value : curList) {
				tree.add(value);
			}
			
//			curList.clear();
		
			Iterator it = tree.iterator();
			while (it.hasNext()) {
				curList.add(it.next().toString());
			}
		
			//save related input variables
			pmmDB.getInputList().add(curList);
		}
	}
	
	@FXML
	public void addPMValue(MouseEvent e) {
		// Add new value
		System.out.println("PM CLICK");
		Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
		int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
	
		if(currentTab == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("No selected tab");
			alert.setContentText("You have to make tab with new tab button");
			alert.showAndWait();
		}else {
			if (!addFile.isVisible()) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("popup/VariablePopUpView.fxml"));
					Parent parent = loader.load();
					Scene scene = new Scene(parent);
	
					valueStage.setTitle("Add Process Model variable");
					valueStage.setResizable(false);
					valueStage.show();
	
					valueStage.setScene(scene);
					valueStage.setOnHidden((new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent e) {
							VariablePopUpController popup = loader.getController();
							//when user inputed text for process model & clicked confirm
							if (popup.value != null && popup.confirmClicked == true) {
								//don't add same value to listview
								for(int i = 0; i < listViewList.size(); i++) {
									if(!listViewList.get(tabIndex).getItems().isEmpty()) {
										String s = listViewList.get(tabIndex).getItems().get(i);
										if(s.equals(popup.value)) {
											return;
										}
									}
								}
								//add to listView
								listViewList.get(tabIndex).getItems().add(popup.value);
								
								//add to db
								for(ProcessModel p : pmmDB.getProcessModel()) {
									System.out.println("add to db");
									if(p.getControllerName().equals(controllerList.getValue()) && p.getControlActionName().equals(currentTab.getText())){
										p.getValuelist().add(popup.value);
									}
								}
							}else if(popup.closeClicked == true) {
								valueStage.close();
							}
						}
					}));
	
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				e.consume();
			}
		}
	}
	
	public void valueListControl() {
		modifyMenu = new MenuItem();
		modifyMenu.textProperty().set("Modify");
		deleteMenu = new MenuItem();
		deleteMenu.textProperty().set("Delete");
		rightClickMenu.getItems().addAll(modifyMenu, deleteMenu);
		
		for(ListView<String> valueList : listViewList) {
			String target = valueList.getSelectionModel().getSelectedItem();
			int targetIndex = valueList.getSelectionModel().getSelectedIndex();
			valueList.setOnMouseClicked(event ->{
				if(event.getButton() == MouseButton.SECONDARY) {
					rightClickMenu.show(valueList, event.getScreenX(), event.getScreenY());
					
					modifyMenu.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							for(ProcessModel p : pmmDB.getProcessModel()) {
								if(p.getControllerName().equals(controllerList.getValue()) && 
										p.getControlActionName().equals(tabPane.getTabs().get(listViewList.indexOf(valueList)).getText())) {
									modifyPopUp(p, targetIndex);
								}
							}
						}
					});
					
					deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							valueList.getItems().remove(targetIndex);
							for(ProcessModel p : pmmDB.getProcessModel()) {
								if(p.getControllerName().equals(controllerList.getValue()) && 
										p.getControlActionName().equals(tabPane.getTabs().get(listViewList.indexOf(valueList)).getText())) {
									p.getValuelist().remove(targetIndex);
								}
							}
						}
					});
				}
				event.consume();
				rightClickMenu.hide();
			});
		}
	}
	
	// Show value edit popup
	public void modifyPopUp(ProcessModel p, int oldvalueIndex) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/VariablePopUpView.fxml"));
		Parent root;

		if (!addFile.isVisible()) {
			try {
				root = loader.load();
				Scene s = new Scene(root);

				valueStage.setScene(s);
				valueStage.setTitle("Modify Process Model variable");
				valueStage.show();

				valueStage.setOnHidden((new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent e) {
						VariablePopUpController popup = loader.getController();
						if (popup.value != null) {
							//modify data in listView
							int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
							listViewList.get(currentTabIndex).getItems().set(oldvalueIndex, popup.value);
							
							//modify data in db
							p.getValuelist().set(oldvalueIndex, popup.value);
						} else
							System.out.println("Please enter a new value.");
					}
				}));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	// Search & remove expressions from value
	public List<String> checkValue(ArrayList<String> valueName, ObservableList<String> outputVariables) {
		//need to be fixed
		String[] expressions = { "<", ">", "=", ":=", "&", "|", "\\+", ">=", "<=", ":" };
		String[] conditions = {"true", "false", "k_", "g_"};
		List<String> values = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
	
		ArrayList<String> innerString = new ArrayList<String>();
		String arg; // plus
		int cnt = 0;
		int next = 0;
		int temp = 0;
	
		// 1. Check expressions in values
		while (cnt < valueName.size()) {
			String str = String.valueOf(valueName.get(cnt));
			if ("null".equals(str))
				break;
			str = str.replace("\n", "");
			str = str.trim();
	
			// Iteration for finding if string has expressions
			for (int j = 0; j < expressions.length;) {
				arg = expressions[j];
				// System.out.println("current string: "+str);
				if (expressions[j].equals("\\+"))
					arg = "+";
	
				// Expression O
				if (str.contains(arg)) {
					// System.out.println("expression : "+arg);
					next = str.indexOf(arg);
					innerString.add(str.substring(0, next));
					for (Object checked : checkValue(innerString, outputVariables)) {
						values.add((String) checked);
					}
					str = str.substring(next + 1);
					j = 0;
					temp = 0;
					continue;
				}
				// Expression X
				else {
					if (temp == 9) {
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
		for (String str : values) {
			int close = 0;
			str = str.trim();
	
			if ("".equals(str))
				continue;
			else if ((conditions[0].equals(str)) || ((conditions[1]).equals(str)) || (conditions[4].equals(str)))
				continue;
			else if (conditions[2].equals(str.substring(0, 2)) || conditions[3].equals(str.substring(0, 2)))
				continue;
			else {
				for (String output : outputVariables) {
					if (str.equals(output)) {
						close = 1;
						break;
					}
				}
				if (close == 1)
					continue;
				else
					result.add(str);
			}
		}
		return result;
	}
	
	private void initialize() {
		System.out.println("\n**********************");
		System.out.println("initialize!! ");
		// PMM, CSE Data Store
		pmmDB = this.mainApp.pmmDB;
		components = this.mainApp.components;
		
		controllerList.getItems().clear();
		
		// From Dashboard to PMM
		if (components.curController == null) {
			// When through file open,
			
			//set Controller ,ControlAction ChoiceBox
		    for(Controller c : components.getControllers()){
		       controllerList.getItems().add(c.getName());
		    }
		    //if Controller Select, Change ControlAction ChoiceBox
		    controllerList.setOnAction(event->{
		       //clear all views
		       tabPane.getTabs().clear();
		       listViewList.clear();
		       showControllerTab(controllerList.getValue());
		       
		       //clear CAList view
		       CAList.getItems().clear();
//		       String controller = controllerList.getValue();
		       //ERROR! Null pointer exception -> don't bring controller name from CSE
		       for(ControlAction c : components.getControlActions()) {
		    	   System.out.println(c.getController() + " : controller");
		    	   System.out.println(c.getCA() + " : control actions");
		    	   if(c.getController() == null) {
		    		   continue;
		    	   }else if(c.getController().getName().equals(controllerList.getValue())) {
		    		   CAList.getItems().addAll(c.getCA());
		    	   }
		       }
		    });
			// while working data in PMM
		} else {
			// CSE -> PMM
//			controllerName = dataStore.getControllerName();
			selectController();
		}
	
		addTabButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//check if tab for selected controller & control action already exists
				for(ProcessModel p : pmmDB.getProcessModel()) {
					if(p.getControllerName().equals(controllerList.getValue()) && p.getControlActionName().equals(CAList.getValue())) {
						return;
					}
				}
				
				Tab newTab = new Tab();
				tabPane.getTabs().add(newTab);
				AnchorPane newPane = new AnchorPane();
				ListView newListView = new ListView();
				listViewList.add(newListView);
				
				newTab.setText(CAList.getValue());
				
				ProcessModel newProcessModel = new ProcessModel();
				newProcessModel.setControllerName(controllerList.getValue());
				newProcessModel.setControlActionName(CAList.getValue());
				pmmDB.getProcessModel().add(newProcessModel);
	
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
	
	// set MainApp
		public void setMainApp(MainApp mainApp) {
			this.mainApp = mainApp;
			this.initialize();
		}
	}
