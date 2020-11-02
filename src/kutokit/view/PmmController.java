package kutokit.view;

import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.Components;
import kutokit.model.ProcessModel;
import kutokit.model.XmlReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.w3c.dom.NodeList;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import kutokit.view.components.Controller;
import kutokit.view.popup.OutputlistPopUpController;

public class PmmController{

	private XmlReader reader;
	private MainApp mainApp;
	private File selectedFile;
	private Components components;
	
	private ProcessModel dataStore;
	private ObservableList<String> valuelist; // value list from xml reader
	private String controllerName, controlAction, outputVariable; // selected controller name, control action from CSE
	
	private Stage outputListStage = new Stage();
	private Stage pmStage = new Stage();
	private ContextMenu contextMenu = new ContextMenu();
	private MenuItem item1, item2, item3;
	
	@FXML private Label fileName;
	@FXML private Pane addFile;
	@FXML private ListView<String> PM;
	@FXML private Rectangle CA;


	public PmmController() {
		valuelist = FXCollections.observableArrayList();
	}

	public void selectController() {
		// Get controllerName, CA from CSE
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/ControllerPopUpView.fxml"));
		OutputlistPopUpController pop = loader.getController();
		/*
		 * 
		 * 
		 * 
		 */
	}

	@FXML
	public void selectPM(MouseEvent e) {
		
        System.out.println("PM CLICK");		
        if( outputVariable != null) {
	  		FXMLLoader loader = new FXMLLoader();
	  		loader.setLocation(getClass().getResource("popup/VariablePopUpView.fxml"));
	  		Parent root;
	  		
	  		if(!outputListStage.isShowing() && !addFile.isVisible()) {
		  		try {
					root = loader.load();
					Scene s = new Scene(root);
				
					pmStage.setScene(s);
					pmStage.show();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		          e.consume();
	  		}
        } else 	System.out.println("Error: select output variable.");

          
	}

	public void addValue() {
		// Add new value 
		System.out.println("add");
	}
	
	@FXML
	public void selectValue(MouseEvent e) {
		// Modify & delete existed values
		 if (e.getButton() == MouseButton.SECONDARY) {
			 	contextMenu.getItems().clear();
			 	contextMenu.hide();
			 	
				item1 = new MenuItem("Modify");
		        item1.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	// modifyValue()
		            }
		        });
		        item2 = new MenuItem("Delete");
		        item2.setOnAction(new EventHandler<ActionEvent>() {
		 
		            @Override
		            public void handle(ActionEvent event) {
		            	// delValue()
		            }
		        });
		        
		        contextMenu.getItems().addAll(item1, item2);

				String target = PM.getSelectionModel().getSelectedItem();
				System.out.println(target);

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
	public void modifyValue() {
		// Modify existed value
		System.out.println("modify");
	}
	public void delValue() {
		// Delete existed value
		System.out.println("delete");
	}
	
	@FXML
	public void openFile(MouseEvent e) {
		if(!outputListStage.isShowing() && !pmStage.isShowing() && outputVariable != null) {
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
        fc.setTitle("Add File");
        fc.setInitialDirectory(new File(Info.directory));
        // Set default directory
        
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
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
		if(selectedFile != null) {
			addFile.getChildren().clear();
			// Create XmlReader constructor
	        reader = new XmlReader(selectedFile.getName());
	        // Make process model
	        // outputVariable = "f_LO_SG1_LEVEL_Trip_Out";
			this.makeModel(reader.getNodeList(reader.getNode(outputVariable),""),
							reader.getTransitionNodes(reader.getNode(outputVariable)));
		}
	}
	

	public void close() {
		addFile.setVisible(false);
	}
	
	// Search & remove expressions from value
	public List checkValue(String[] valueName) {

		String[] expressions = { "<", ">", "=", ":=", "&", "|", "\\+", ">=", "<=", ":" };
		String[] conditions = { "true", "false", outputVariable, "k_", "g_"}; 
		List<String> values = new ArrayList();
		List<String> result = new ArrayList();

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
					for(Object checked: checkValue(innerstr)) {
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
			str = str.trim();
			if("".equals(str)) continue;
			else if ((conditions[0].equals(str)) || ((conditions[1]).equals(str)) || (conditions[2].equals(str))) continue;
			else if( conditions[3].equals(str.substring(0,2)) || conditions[4].equals(str.substring(0,2))) continue;
			else {
				result.add(str);
			}
		}
		return result;
	}
	
	// Make process model 
	public void makeModel(NodeList l1, List<String> l2) {
		String nodeType = outputVariable.substring(0,1);
		String[] valueName = new String[10];
		List checkedl1 = new ArrayList();
		List checkedl2 = new ArrayList();

		// SDT : L1+L2, TTS : L2
		// Get input variables from l1
		if( nodeType.equals("f") ) {
			for(int i = 0 ; i< l1.getLength(); i++) {
				String str = l1.item(i).getAttributes().getNamedItem("value").getTextContent();
				valueName[i] = str;
			}
			checkedl1 = checkValue(valueName);
			for(Object value : checkedl1) {
				this.valuelist.add(value.toString());
			}	 
		}
		
		// Get transition variables from l2
		for(int i = 0 ; i< l2.size(); i++) {
			valueName[i] = l2.get(i);
		}
		checkedl2 = checkValue(valueName);
		for(Object value : checkedl2) {
			this.valuelist.add(value.toString());
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
	}
	
	public void savePM(String name, List list) {
		
	}

	private void initialize() {
		dataStore = mainApp.models;
		controllerName = dataStore.getControllerName();
		controlAction = dataStore.getControlActionName();
		outputVariable = dataStore.getOutputName();
		valuelist = dataStore.getValuelist();
	}
	
	// Show output variables list popup
	public void addListPopUp() {
		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource("popup/OutputlistPopUpView.fxml"));
  		Parent root;

  		try {
  			OutputlistPopUpController popup = loader.getController();
			root = (Parent)loader.load();
			Scene s = new Scene(root);
			  			
			outputListStage.setScene(s);
			outputListStage.show();

			outputListStage.setOnHidden((new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent e) {
			    	dataStore.setOutputName(popup.output);
					outputVariable = dataStore.getOutputName();
			    	System.out.println("outputVariable: "+outputVariable);
			    }
			  }));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.initialize();
		addListPopUp();
	}
}
