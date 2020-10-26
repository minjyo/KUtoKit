package kutokit.view;

import kutokit.Info;
import kutokit.MainApp;
import kutokit.model.XmlReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.w3c.dom.NodeList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PmmController {

	private XmlReader reader;
	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<String> valuelist;
	private static String controller; // selected controller name 
	@FXML private Label filename;
	@FXML private Pane AddFile;
	@FXML private ListView<String> PM;
	
	//constructor
	public PmmController() {
		valuelist = FXCollections.observableArrayList();
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        fc.setInitialDirectory(new File(Info.directory));
        // Set default directory
        
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        filename.setText(selectedFile.getName());

	        try {
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
        }
    }
	
	@FXML
	public void ApplyFile() {
		if(selectedFile != null) {
			AddFile.getChildren().clear();
			// Create XmlReader constructor
	        reader = new XmlReader(selectedFile.getName());
	        // Make process model
	        controller = "th_LO_SG1_LEVEL_Trip_Logic";
			this.makeModel(reader.getNodeList(reader.getNode(controller),""),
							reader.getTransitionNodes(reader.getNode(controller)));
		}
	}
	
	// Search & remove expressions from value
	public static List checkValue(String[] valueName) {

		String[] expressions = { "<", ">", "=", ":=", "&", "|", "\\+", ">=", "<=", ":" };
		String[] conditions = { "true", "false" }; 
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
			if ((conditions[0].equals(str)) || ((conditions[1]).equals(str))) continue;
			else {
				result.add(str);
			}
		}
		return result;
	}
	
	// Make process model 
	public void makeModel(NodeList l1, List<String> l2) {
		String nodeType = controller.substring(0,1);
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
		PM.setItems(valuelist);
	}
	
	
	public void savePM(String name, List list) {
		
	}
}
