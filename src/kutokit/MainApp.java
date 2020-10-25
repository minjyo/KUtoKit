package kutokit;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kutokit.view.CseController;
import kutokit.view.PmmController;
import kutokit.view.RoadMap;
import kutokit.view.CtmController;
import kutokit.view.LhcController;
import kutokit.view.UtmController;
import kutokit.view.RootLayoutController;
import kutokit.model.*;

public class MainApp extends Application {
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	 private CtmController controller;
	 
	 public Components components;
	 
	@Override
	//auto execute after main execute
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Kutokit");

	        initRootLayout();
	        initDataStore();
	}

	 /**
     * init root layout
     */
	
	private void initDataStore() {
		components = new Components();
	}
	
	private void initRootLayout() {
		try {
            // get root layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //add root layout to scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            //add controller
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		File file = getContextTableFilePath();
		if(file != null) {
			loadContextTableDataFromFile(file);
		}
	}
	
	/**
	 * called when LHC button clicked
	 */
	public void showLhcView() {
        try {
            // get table scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LhcView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            LhcController controller = loader.getController();
            controller.setMainApp(this);
            
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
     * called when cseButton clicked
     */
    public void showCseView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CseView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            CseController controller = loader.getController();
            controller.setMainApp(this, primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * called when ctmButton clicked
     */
    public void showCtmView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CtmView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            controller = loader.getController();
            controller.setMainApp(this);
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * called when utmButton clicked
     */
    public void showUtmView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UtmView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            rootLayout.setCenter(View);
            
            //add controller
            UtmController controller = loader.getController();
            controller.setUcaTable(getContextTable());
            controller.setMainApp(this);
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * called when pmmButton clicked
     */
    public void showPmmView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PmmView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            PmmController controller = loader.getController();
            controller.setMainApp(this);
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //called when help button clicked
	public void showRoadMapView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RoadMapView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            RoadMap controller = loader.getController();
            controller.setMainApp(this);
            
            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
	 * return main stage
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}


	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	
	public File getContextTableFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * 占쏙옙占쏙옙 占쌀뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙罐占� 占쏙옙占쏙옙占싼댐옙. 占쏙옙 占쏙옙灌占� OS 특占쏙옙 占쏙옙占쏙옙占쏙옙트占쏙옙占쏙옙 占쏙옙占쏙옙홱占�.
	 *
	 * @param file the file or null to remove the path
	 */
	public void setContextTableFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Stage 타占쏙옙틀占쏙옙 占쏙옙占쏙옙占쏙옙트占싼댐옙.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Stage 타占쏙옙틀占쏙옙 占쏙옙占쏙옙占쏙옙트占싼댐옙.
	        primaryStage.setTitle("AddressApp");
	    }
	}
	
	/**
	 * 占쏙옙占쏙옙占쏙옙 占쏙옙占싹로븝옙占쏙옙 ContextTable占쏙옙 占쏙옙占쏙옙占승댐옙.
	 * @param file
	 */
	public void loadContextTableDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ContextTableWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // 占쏙옙占싹로븝옙占쏙옙 XML占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙占쏙옙占싼댐옙.
	        ContextTableWrapper wrapper = (ContextTableWrapper) um.unmarshal(file);

	        //ContextTableDataModel.clear();
	        //ContextTableDataModel.addAll(wrapper.getContextTableDataModel());

	        // 占쏙옙占쏙옙 占쏙옙罐占� 占쏙옙占쏙옙占쏙옙트占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
	        setContextTableFilePath(file);

	    } catch (Exception e) { // 占쏙옙占쌤몌옙 占쏙옙쨈占�
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * 占쏙옙占쏙옙 ContextTable占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占싹울옙 占쏙옙占쏙옙占싼댐옙.
	 * @param file
	 */
	public void saveContextTableDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ContextTableWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        ContextTableWrapper wrapper = new ContextTableWrapper();
	        wrapper.setContextTables(controller.getContextTableData());
	        
	        //test
	        System.out.println(controller.getContextTableData());
	        
	        
	        
	        // 占쏙옙占쏙옙占쏙옙 占쏙옙 XML占쏙옙 占쏙옙占싹울옙 占쏙옙占쏙옙占싼댐옙.
	        m.marshal(wrapper, file);

	        // 占쏙옙占쏙옙 占쏙옙罐占� 占쏙옙占쏙옙占쏙옙트占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
	        setContextTableFilePath(file);
	    } catch (Exception e) { // 占쏙옙占쌤몌옙 占쏙옙쨈占�.
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	//ContextTable(myTable) 遺덈윭�삤湲�
	private ObservableList<CTM> getContextTable()
	{
		ObservableList<CTM> ctmList = null;
		try {
			// get maker scene
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CtmView.fxml"));
			AnchorPane View = (AnchorPane) loader.load();
			CtmController controller = loader.getController();
			ctmList = controller.getContextTableData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ctmList;
	}
}
