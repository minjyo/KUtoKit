package kutokit.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
	ArrayList<ArrayList<String>> inputList = new ArrayList<ArrayList<String>>();

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
		valueListControl(newListView);

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
		System.out.println(selectedFile);
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
	    outputList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create XmlReader constructor
		xmlReader = new XmlReader(selectedFile.toString());
		if(xmlReader.getRootFod() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("File doesn't fit NuSCR format");
			alert.setContentText("You have to apply NuSCR file");
		}
		ArrayList<String> showGroupNodesItems = new ArrayList<String>();

		//show popup to select group FODs from NuSRS file
		try {
			System.out.println("Please select group FODs.");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("popup/SelectGroupFODView.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			Stage selectGroupStage = new Stage();

			selectGroupStage.setTitle("Select output variable groups");
			selectGroupStage.setResizable(false);
			selectGroupStage.show();

			selectGroupStage.setScene(scene);

			SelectGroupFODController FODcontroller = loader.getController();
			FODcontroller.setRootFOD(XmlReader.getRootFod().getNodeName());
			for(Node fod: XmlReader.showValidFods()) {
				showGroupNodesItems.add(fod.getAttributes().getNamedItem("name").getNodeValue());
			}
			
			FODcontroller.setGroupItems(showGroupNodesItems);
			
			selectGroupStage.setOnHidden((new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					if(FODcontroller.canceled == true) {
						selectGroupStage.close();
					}else if(FODcontroller.confirmed == true) {
						// outputList.getItems().addAll(FODcontroller.selectedItems());
						
						// if you select FOD, get outputs about selected FOD
						ObservableList<String> outputlist = FXCollections.observableArrayList();
						for(String fod : FODcontroller.selectedItems()) {
							XmlReader.setRootFod(fod);

							// VIEW
							List<String> outputs = XmlReader.getOutputs();
							// System.out.println("outputs: "+outputs);
							for (String data : outputs) {
								outputlist.add(data);
							}
						}
						for(int a = 0; a < outputlist.size(); a++) {
							System.out.println("before : " + outputlist.get(a));
						}
						
						//if output list has redundant data, remove from list
						for(int i = 0; i < outputlist.size(); i++) {
							for(int j = 0; j < outputlist.size(); j++) {
								if(i < j && outputlist.get(i).equals(outputlist.get(j))) {
									outputlist.remove(j);
								}
							}
						}
						for(int b = 0; b < outputlist.size(); b++) {
							System.out.println("after : " + outputlist.get(b));
						}
						outputList.setItems(outputlist);
						pmmDB.setOutputList(outputlist);
						makeModel(outputlist);
					}
				}
			}));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}

	@FXML
    public void addToProcessModel() {
		
		//add selected value from output list to value list
	    outputList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    //makeModel(outputList.getSelectionModel().getSelectedItems());
		
		System.out.println("선택한 output : "+outputList.getSelectionModel().getSelectedItems());
	    ProcessModel PM = new ProcessModel();
	    for(ProcessModel pm : pmmDB.getProcessModel()){
	        if(pm.getControlActionName().equals(CAList.getValue()) && pm.getControllerName().equals(controllerList.getValue())){
	           PM = pm;
	        }
	     }

	    ObservableList<String> selectedOutputs = FXCollections.observableArrayList();
	    selectedOutputs.addAll(outputList.getSelectionModel().getSelectedItems());
	//    ArrayList<String> newValues = new ArrayList<String>();

	    if (selectedFile != null && !selectedOutputs.isEmpty()) {
	        ListView<String> lv = new ListView<String>();
	        valueListControl(lv);
	        for(Tab tab : tabPane.getTabs()){
	        	if(tab.getText().equals(CAList.getValue())){
	        		lv = listViewList.get(tabPane.getTabs().indexOf(tab));
	            }
	        }
	        //for selectedOutputs, add related input datas in db
	        for(String selectedOutput : selectedOutputs){
	        	int index = outputList.getItems().indexOf(selectedOutput);
	            System.out.println("index:"+index);
	            if(!pmmDB.getInputList().isEmpty()) {
	            	System.out.println("DB에 inputlist가 존재합니다.");
	            	System.out.println(pmmDB.getInputList());
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
	            }else {
	            	System.out.println("no input list data in db");
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
	
		ArrayList<String> connectedValues = new ArrayList<String>();
		NodeList directlyConnectedNode;
		List<String> transitionNodes = new ArrayList<String>();

		List<String> directlyConnectedNodeList = new ArrayList<String>();
		List<String> transitionNodeList = new ArrayList<String>();

		ObservableList<String> innerList = FXCollections.observableArrayList();

		for(int i=0; i<outputlist.size(); i++) {
			ArrayList<String> curList = new ArrayList<String>();
			innerList.clear();
			curList.clear();
			connectedValues.clear();
			
			String nodeType = outputlist.get(i).substring(0, 1);
			try {
				//directly connected nodes
				directlyConnectedNode = XmlReader.getNodeList(XmlReader.getNode(outputlist.get(i)), "");
				
				if(directlyConnectedNode != null) {
					String str;
					// Get related variables from SDT/FSM/TTS nodes
					if (nodeType.equals("f") || nodeType.equals("t") || nodeType.equals("h")) {
						for (int j = 0; j < directlyConnectedNode.getLength(); j++) {
							if(nodeType.equals("f")) {
								str = directlyConnectedNode.item(j).getAttributes().getNamedItem("value").getTextContent();
							}
							else str = directlyConnectedNode.item(j).getAttributes().getNamedItem("name").getTextContent();
							connectedValues.add(str);
						}
						directlyConnectedNodeList = checkValue(connectedValues, outputlist.get(i));
						for (Object value : directlyConnectedNodeList) {
							curList.add(value.toString());
						}
	
					}
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
			finally {
				try {
					innerList.clear();
					//other connected nodes connected to directly connected nodes
					transitionNodes = xmlReader.getTransitionNodes(xmlReader.getNode(outputlist.get(i)));
			
					// Get transition variables from l2
					//FSM, TTS node
					for(String value : transitionNodes) {
						connectedValues.add(value);
					}
					
					transitionNodeList = checkValue(connectedValues, outputlist.get(i));
					for (Object value : transitionNodeList) {
						curList.add(value.toString());
					}

					
				} catch(NullPointerException e) {
					e.printStackTrace();
				} finally {
					
					// Add input nodes
					for(Node input: XmlReader.getInputs()) {
						curList.add(input.getAttributes().getNamedItem("name").getTextContent());						
					}
					
					// Remove redundant variables between l1 and l2
					
					TreeSet tree = new TreeSet();
					for (String value : curList) {
						tree.add(value);
					}
					curList.clear();
					Iterator it = tree.iterator();
					while (it.hasNext()) {
						curList.add(it.next().toString());
					}

					System.out.println("i:"+i+","+curList);
					//save related input variables
					pmmDB.getInputList().add(curList);
				}
			}
		}
	}
	
	// Search & remove expressions from value
	public List<String> checkValue(ArrayList<String> valueName, String curOutput) {
		//need to be fixed
		String[] expressions = { "<", ">", "=", ":=", "&", "|", "\\+", ">=", "<=", ":" };
		String[] conditions = {"true", "false", "k_", "g_", curOutput};
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
					for (Object checked : checkValue(innerString, curOutput)) {
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
			boolean isNumber = true;
			int close = 0;
			str = str.trim();
	
			try{
		        Integer.parseInt(str);
		    } catch(NumberFormatException e) {
		    	isNumber = false;
		    } catch(NullPointerException e) {
		    	isNumber = false;
		    } 
			 
			if ("".equals(str))
				continue;
			else if(isNumber)
				continue;
			else if ((conditions[0].equals(str)) || ((conditions[1]).equals(str)) || (conditions[4].equals(str)))
				continue;
			else if (conditions[2].equals(str.substring(0, 2)) || conditions[3].equals(str.substring(0, 2)))
				continue;
			else {
					result.add(str);
			}
		}
		return result;
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

					valueStage.initModality(Modality.WINDOW_MODAL);
					valueStage.initOwner(mainApp.getPrimaryStage());

					valueStage.setTitle("Add Process Model variable");
					valueStage.setResizable(false);
					valueStage.show();

					valueStage.setScene(scene);
					valueStage.setOnHidden(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent e) {
							VariablePopUpController popup = loader.getController();
							if (popup.value != null) {
								//don't add same value to listview
								if(!listViewList.get(tabIndex).getItems().isEmpty()) {
									for(String s : listViewList.get(tabIndex).getItems()) {
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
						}
					}});

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				e.consume();
			}
		}
	}

	public void valueListControl(ListView<String> valueList) {
		ContextMenu rightClickMenu = new ContextMenu();
		int listIndex = listViewList.indexOf(valueList);
		
		modifyMenu = new MenuItem("Modify");
		deleteMenu = new MenuItem("Delete");
		rightClickMenu.getItems().addAll(modifyMenu, deleteMenu);

		int targetListIndex = valueList.getSelectionModel().getSelectedIndex();

		deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
		     @Override
	         public void handle(ActionEvent event) {
	    		 int targetIndex = valueList.getSelectionModel().getSelectedIndex();
		    	 try{
					valueList.getItems().remove(targetIndex);
					for(ProcessModel p : pmmDB.getProcessModel()) {
						if(p.getControllerName().equals(controllerList.getValue()) &&
							p.getControlActionName().equals(tabPane.getTabs().get(listViewList.indexOf(valueList)).getText())) {
							p.getValuelist().remove(targetIndex);
						}
					}
		    	 }catch(Exception e){
		    		 System.out.println("Select empty data");
		    	 }
	         }
	        });

		modifyMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)
			{
				int targetIndex = valueList.getSelectionModel().getSelectedIndex();
				try{
					for(ProcessModel p : pmmDB.getProcessModel()) {
						if(p.getControllerName().equals(controllerList.getValue()) &&
							p.getControlActionName().equals(tabPane.getTabs().get(listViewList.indexOf(valueList)).getText())) {
							modifyPopUp(p, targetIndex,valueList);
						}
					}
				}catch(Exception e){
						System.out.println("Select empty data");
				}
			}
		});

		valueList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
	        public void handle(ContextMenuEvent event) {
				rightClickMenu.show(valueList, event.getScreenX(), event.getScreenY());
	        }
	    });
//		rightClickMenu.hide();
	}

	// Show value edit popup
	public void modifyPopUp(ProcessModel p, int oldvalueIndex,ListView<String> curList) {
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
							try{
								int currentTabIndex = listViewList.indexOf(curList);
								listViewList.get(currentTabIndex).getItems().set(oldvalueIndex, popup.value);
								//modify data in db
								p.getValuelist().set(oldvalueIndex, popup.value);
							} catch(Exception ex){
								System.out.println("Modify ValueList Error");
							}
						} else
							System.out.println("Please enter a new value.");
					}
				}));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
				ListView<String> newListView = new ListView<String>();
				valueListControl(newListView);
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