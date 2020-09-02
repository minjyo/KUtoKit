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
        fc.setInitialDirectory(new File("C:/")); // default 디렉토리 설정
        
        // 확장자 제한
        ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
        fc.getExtensionFilters().addAll(txtType);
         
	    selectedFile =  fc.showOpenDialog(null);
        if(selectedFile != null) {
	        //System.out.println(selectedFile);  // 선택한 경로 출력
	        //System.out.println(selectedFile.getName());
	        filename.setText(selectedFile.getName());
	         
	        // 파일을 InputStream으로 읽어옴
	        try {
	            // 파일 읽어오기
	            FileInputStream fis = new FileInputStream(selectedFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            //System.out.println(bis);  // 선택한 파일 출력
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
             * 파싱 코드 추가
             * 
             */
            
		}
	}
	
}
