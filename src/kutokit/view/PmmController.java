package kutokit.view;

import kutokit.MainApp;
import kutokit.model.xmlTest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

	private xmlTest reader;
	private MainApp mainApp;
	private File selectedFile;
	private ObservableList<String> valuelist;
	
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
    
         fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
//        fc.setInitialDirectory(new File("/Users/minjyo/eclipse-workspace/KUtoKit/"));
        

        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile);  // �꽑�깮�븳 寃쎈줈 異쒕젰
	        //System.out.println(selectedFile.getName());
	        filename.setText(selectedFile.getName());
	         
	        // �뙆�씪�쓣 InputStream�쑝濡� �씫�뼱�샂
	        try {
	            // �뙆�씪 �씫�뼱�삤湲�
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            //System.out.println(bis);  // �꽑�깮�븳 �뙆�씪 異쒕젰
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
        }
    }
	
	@FXML
	public void ApplyFile() {
		if(selectedFile != null) {
			AddFile.getChildren().clear();
			// this.showList();
	        reader = new xmlTest(selectedFile.getName());
			this.makeModel(reader.getNodeList(reader.getNode("f_LO_SG1_LEVEL_Val_Out"), "/condition"));
	        this.makeModel(reader.getNodeList(reader.getNode("f_LO_SG1_LEVEL_Val_Out"), "/action")); 
		}
	}
	
	/* Select SDT(TTS or FSM)
	public void showList() {
		ListView sdtList = new ListView();
    	ObservableList<String> valuelist = 
    			FXCollections.observableArrayList("f_LO_SG1_LEVEL_Val_Out","f_LO_SG1_LEVEL_PV_Err","f_LO_SG1_LEVEL_Ptrp_Out","f_LO_SG1_LEVEL_Trip_Out");
		sdtList.setItems(valuelist);
		AddFile.getChildren().add(sdtList);
		
	}
	*/
	
	// Make process model 
	public void makeModel(NodeList list) {
		for(int i = 0 ; i< list.getLength(); i++) {
			this.valuelist.add(list.item(i).getAttributes().getNamedItem("value").getTextContent());
		}
		PM.setItems(valuelist);
	}
}
