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
import kutokit.view.DashboardController;
import kutokit.view.CtmController;
import kutokit.view.LhcController;
import kutokit.view.UtmController;
import kutokit.view.RootLayoutController;
import kutokit.model.*;

public class MainApp extends Application {
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	 private CtmController controller;
	 
	 public static Components components;
	 
	@Override
	//auto execute after main execute
	public void start(Stage primaryStage) {
		 	this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Kutokit");
	        this.primaryStage.setResizable(false);
	        
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
	public void showDashboardView() {
        try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DashboardView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            DashboardController controller = loader.getController();
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
	 * �뜝�룞�삕�뜝�룞�삕 �뜝��琉꾩삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕營먨뜝占� �뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕. �뜝�룞�삕 �뜝�룞�삕�걣�뜝占� OS �듅�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�솼�뜝占�.
	 *
	 * @param file the file or null to remove the path
	 */
	public void setContextTableFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Stage ���뜝�룞�삕���뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃�뜝�떬�뙋�삕.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Stage ���뜝�룞�삕���뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃�뜝�떬�뙋�삕.
	        primaryStage.setTitle("AddressApp");
	    }
	}
	
	/**
	 * �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�떦濡쒕툦�삕�뜝�룞�삕 ContextTable�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�듅�뙋�삕.
	 * @param file
	 */
	public void loadContextTableDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ContextTableWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // �뜝�룞�삕�뜝�떦濡쒕툦�삕�뜝�룞�삕 XML�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕.
	        ContextTableWrapper wrapper = (ContextTableWrapper) um.unmarshal(file);

	        //ContextTableDataModel.clear();
	        //ContextTableDataModel.addAll(wrapper.getContextTableDataModel());

	        // �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕營먨뜝占� �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕.
	        setContextTableFilePath(file);

	    } catch (Exception e) { // �뜝�룞�삕�뜝�뙟紐뚯삕 �뜝�룞�삕夷덂뜝占�
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * �뜝�룞�삕�뜝�룞�삕 ContextTable�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�떦�슱�삕 �뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕.
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
	        
	        
	        
	        // �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕 XML�뜝�룞�삕 �뜝�룞�삕�뜝�떦�슱�삕 �뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕.
	        m.marshal(wrapper, file);

	        // �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕營먨뜝占� �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�떬�뙋�삕.
	        setContextTableFilePath(file);
	    } catch (Exception e) { // �뜝�룞�삕�뜝�뙟紐뚯삕 �뜝�룞�삕夷덂뜝占�.
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	//ContextTable(myTable) �겫�뜄�쑎占쎌궎疫뀐옙
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
