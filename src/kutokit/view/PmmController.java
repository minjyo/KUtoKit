package kutokit.view;

import kutokit.MainApp;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PmmController {

	private MainApp mainApp;
	private File selectedFile;
	@FXML private Label filename;
	@FXML private Pane AddFile;
	
	//constructor
	public PmmController() {
		
	}

	//set MainApp
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void AddFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add File");
        fc.setInitialDirectory(new File("C:/")); // default �뵒�젆�넗由� �꽕�젙
        
        // �솗�옣�옄 �젣�븳

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
            
            /*
             * �뙆�떛 肄붾뱶 異붽�
             * 
             */
            
		}
	}
}
