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

	// 새 tab에 list controller, control action 연결하기
	private XmlReader reader;
	private MainApp mainApp;
	private File selectedFile;
	private Components components;
	
	private ProcessModel dataStore;
	private ArrayList<String> controllerName = new ArrayList<String>(); // Selected controller
	private ArrayList<String> controlAction[]; // 모든 control action
	private ArrayList<String> selectedCA[]; // 선택된 control action
	private ArrayList<String> selectedOutput[]; // 선택된 output
	
	private ObservableList<String> allOutput = FXCollections.observableArrayList(); // 추출된 output
	private ObservableList<String> valuelist = FXCollections.observableArrayList(); // 추출된 value list
	private ObservableList<String> list = FXCollections.observableArrayList(); 

	private Stage valueStage = new Stage();
	private ContextMenu contextMenu = new ContextMenu();
	private MenuItem item1, item2;
	public String curOutput;
	public int curIndex;
	
	@FXML private Label fileName, CANameBar;
	@FXML private Pane addFile;
	@FXML private ChoiceBox<String> controllerList, CAList;
	@FXML private ListView<String> outputList, PM;
	@FXML private TabPane tabPane;
	@FXML private Tab tab_1; // default tab
	@FXML private AnchorPane newPane;
	
	PmmTabPopUpController c;
	
	public PmmController() {

	}

	// Get Controller, all of CA from CSE DataStore
	public void selectController() {	
		// CSE -> PMM 모드 진입 
		// 선택된 controller에 대한 정보 가져오기
		Controller controller = components.curController;
			System.out.println("controllerame: "+controllerName);
			
			if( controllerName.contains(controller.getName())) {
				// 이미 같은 이름의 controller tab이 존재할 때, 
				System.out.println("이미 같은 controller가 존재하네요.");
			} 
			
			else {
				// 새로운 controller 추가
				System.out.println("Add new Controller");
				
				// Create new tab
				if(!dataStore.getControllerName().isEmpty()) {
					// 이전에 추가해둔 controller가 존재할때, 배열에 가져오기 
					System.out.println("이전에 추가한 controller가 있네요. : "+dataStore.getControllerName());
					controllerName.addAll(dataStore.getControllerName());
				}
				// 이후 새로운 controller 배열에 추가
				controllerName.add(controller.getName());
				System.out.println("저장된 controller: "+controllerName);
				System.out.println("controllerName size: "+controllerName.size()+", tabsize: "+tabPane.getTabs().size());
				
				if(controllerName.size() != tabPane.getTabs().size()) {
					System.out.println("Create new tab for new controller.");
					addTab(tabPane);
				}
			}
			
			curIndex = controllerName.indexOf(controller.getName());
			System.out.println("현재 controller index (curIndex) : "+curIndex);
			Map<Integer, Integer> controlActions = controller.getCA();
			controlAction = new ArrayList[controlActions.size()];
			selectedCA = new ArrayList[controlAction.length];
			selectedOutput = new ArrayList[controlActions.size()];
				
			int i=0;
			for(Integer ca : controlActions.keySet()) {
				
				if(i > controlAction.length-1) break;
				if(!dataStore.isAllCAEmpty()) {
					controlAction = dataStore.getAllCA();
					i = dataStore.getAllCAsize();
				}
				controlAction[i] = components.findControlAction(ca).getCA();
				i++;
			}
			
			for(ArrayList<String> list : controlAction) {
				System.out.println("After->list: "+list);
			}
			
			dataStore.setAllCA(controlAction);
			
			controllerList.getItems().addAll(controllerName);
			CAList.getItems().addAll(controlAction[curIndex]);
	}
	
	@FXML
	public void showOutput() {
		// Save selected controller, ca in datastore
		dataStore.setControllerName(controllerName);
		String curCA = CAList.getSelectionModel().getSelectedItem();
		CANameBar.setText(curCA);

		System.out.println("@@@@@@@@@@@");
		System.out.println("selectedCA.length : "+selectedCA.length);
		System.out.println("curIndex : "+curIndex);
		System.out.println("curCA : "+curCA);
		selectedCA[curIndex].add(curCA);
		System.out.println("before dataStore.ca : "+dataStore.getControlActionName());
		System.out.println("selectedCA: "+selectedCA);
		dataStore.setControlActionName(selectedCA);
		
		System.out.println("after dataStore.ca: "+dataStore.getControlActionName());
		for(ArrayList<String> list : dataStore.getAllCA()) {
			System.out.println("All input: "+list);
		}
		addFile.setVisible(true);
	
	}

	public void addTab(TabPane tabpane) {
		Tab newtab = new Tab("Controller 2");
		
		// tab에 가져온 pane을 붙여서 tabPane에 tab 추가
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("popup/PmmTabPopUpView.fxml"));
			this.newPane = pane;
			newtab.setContent(this.newPane);
			tabpane.getTabs().add(newtab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void selectPM(MouseEvent e) {
		
		// Add new value
        System.out.println("PM CLICK");		
        if( selectedOutput != null) {
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
		if(!valueStage.isShowing() && selectedOutput != null) {
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
		addFile.getChildren().clear();
		PM.getItems().clear();
		// Create XmlReader constructor
        reader = new XmlReader(selectedFile.getName());
        
		// Make process model
		if(selectedFile != null && !allOutput.isEmpty()) { 
			for(ArrayList<String> list: selectedOutput) {
				System.out.println(list);
			}
			// Save selected Output
			for(String data : list) {
				selectedOutput[curIndex].add(data);
			}
			dataStore.setOutputNames(selectedOutput);
			
			System.out.println("프로세스 모델 생성");
			System.out.println(selectedFile);
			this.makeModel(selectedOutput);
		}  
		
		// Get output variables
		else if( allOutput.isEmpty() ){
			System.out.println("output 변수 추출");
			List<String> list = reader.getOutputs();
			for(String data : list) {
				allOutput.add(data);
			}
			dataStore.setAllOutput(allOutput);
			outputList.setItems(allOutput);
		}
		close();
	}
	
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
	public void makeModel(ArrayList<String>[] selectedOutputs) {
		
		String[] valueName = new String[50];
		NodeList l1;
		List<String> l2 = new ArrayList<String>();
		
		List<String> checkedl1 = new ArrayList<String>();
		List<String> checkedl2 = new ArrayList<String>();
		
		for(String output : selectedOutputs[curIndex]) {
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
				checkedl1 = checkValue(valueName, selectedOutputs[curIndex]);
				for(Object value : checkedl1) {
					this.valuelist.add(value.toString());
				}	 
			}
			
			// Get transition variables from l2
			for(int i = 0 ; i< l2.size(); i++) {
				valueName[i] = l2.get(i);
			}
			checkedl2 = checkValue(valueName, selectedOutputs[curIndex]);
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
		
		dataStore.setValuelist(valuelist);
		PM.setItems(valuelist);		
		System.out.println("최종 저장된 controller name: "+controllerName);
		System.out.println("최종 저장된 control action: "+controlAction[0]+","+controlAction[1]);
	}

	private void initialize() {
		
		// PMM, CSE Data Store
		dataStore = this.mainApp.models;
		components = this.mainApp.components;

		// From Dashboard to PMM
		if( components.curController == null) { 
			
			// When through file open,
			if((!dataStore.getControllerName().isEmpty()) && controllerName.isEmpty()) {
				controllerName = dataStore.getControllerName();
				controlAction = dataStore.getAllCA();
			} 
			else if(dataStore.getControllerName().isEmpty() && (!components.getControllers().isEmpty())){
				// not file open
				System.out.println("완성된 control structure의 정보를 가져옵니다.");
				int i=0;
				for(Controller c : components.getControllers()) {
					controllerName.add(i, c.getName());
					i++;
				}
				
				 //Add all ca in list with selected controller
//				controllerList.setOnMouseClicked((MouseEvent e) ->{
//					  controllerName.add(= outputList.getSelectionModel().getSelectedItems();
//				});	
//				curIndex = controllerName.indexOf(o)
	
			}
		} else { 
			// CSE -> PMM
			selectController();
		}

		// Data
		selectedFile = dataStore.getFilePath();
		selectedCA = dataStore.getControlActionName();
		selectedOutput = dataStore.getOutputNames();
		allOutput = dataStore.getAllOutput();
		valuelist = dataStore.getValuelist();

		// View 
		outputList.getItems().addAll(dataStore.getAllOutput());
		outputList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list = outputList.getSelectionModel().getSelectedItems();
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

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.initialize();
	}
}
