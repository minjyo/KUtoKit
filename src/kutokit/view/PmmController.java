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
        // minjyo - mac
        //fc.setInitialDirectory(new File("/Users/minjyo/eclipse-workspace/KUtoKit/"));
        
        // 占쎌넇占쎌삢占쎌쁽 占쎌젫占쎈립

        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile);  // 占쎄퐨占쎄문占쎈립 野껋럥以� �빊�뮆�젾
	        //System.out.println(selectedFile.getName());
	        filename.setText(selectedFile.getName());
	         
	        // 占쎈솁占쎌뵬占쎌뱽 InputStream占쎌몵嚥∽옙 占쎌뵭占쎈선占쎌긾
	        try {
	            // 占쎈솁占쎌뵬 占쎌뵭占쎈선占쎌궎疫뀐옙
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            //System.out.println(bis);  // 占쎄퐨占쎄문占쎈립 占쎈솁占쎌뵬 �빊�뮆�젾
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
             * 占쎈솁占쎈뼓 �굜遺얜굡 �빊遺쏙옙
             * 
             */
            
		}
	}
}
