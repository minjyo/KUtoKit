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
import kutokit.model.ProjectXML;
import kutokit.model.cse.Components;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LHCDataStore;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCADataStore;

public class MainApp extends Application {

	 private Stage primaryStage;
	 private BorderPane rootLayout;
	 private CtmController controller;

	 public static Components components;
	 public static LHCDataStore lhcDataStore;
	 private ObservableList<LHC> lhcList;
	 public ProcessModel models;
	 public static UCADataStore ucadatastore;
//	 private static CTMDataStore ctmdatastore;

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
		lhcDataStore = new LHCDataStore();
		models = new ProcessModel();
		ucadatastore = new UCADataStore();
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
           // controller.setUcaTable(getContextTable());
            controller.setMainApp(this);

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

	public File getFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	    	//System.out.println("filePath: " + filePath);
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	public void setFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());
	    } else {
	        prefs.remove("filePath");
	    }
	}

	public void openFile(File file) {
		 try {

		     	//CSE
		        JAXBContext context = JAXBContext
		                .newInstance(ProjectXML.class);
		        Unmarshaller um = context.createUnmarshaller();

		        ProjectXML projectXML = (ProjectXML) um.unmarshal(file);

//		     // --------------------------- CSE --------------------------
//		        components.getControllers().addAll(projectXML.getControllers());
//		        components.getControlActions().addAll(projectXML.getControlActions());
//		        components.getFeedbacks().addAll(projectXML.getFeedbacks());
//		     // --------------------------- CSE --------------------------
//
//
//		     // --------------------------- UTM --------------------------
//		        ucadatastore.getUCATableList().addAll(projectXML.getUCAList());
//		     // --------------------------- UTM --------------------------

			 // --------------------------- PMM --------------------------
		        models.setControllerName(projectXML.getControllerName());;
		        models.setControlActionName(projectXML.getControlActionName());
		        models.setOutputName(projectXML.getOutputVariableName());
		        models.getValuelist().addAll(projectXML.getValueList());
			 // --------------------------- PMM --------------------------

		        setFilePath(file);

		    } catch (Exception e) {
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Could not load data");
		        alert.setContentText("Could not load data from file:\n" + file.getPath());

		        alert.showAndWait();
		    }
	}

	public void saveFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ProjectXML.class);

	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        ProjectXML projectXML = new ProjectXML();

	     // --------------------------- CSE --------------------------
	        projectXML.setControllers(components.getControllers());
	        projectXML.setControlActions(components.getControlActions());
	        projectXML.setFeedbacks(components.getFeedbacks());
	     // --------------------------- CSE --------------------------


	     // --------------------------- UTM --------------------------
	        projectXML.setUCAList(ucadatastore.getUCATableList());
	     // --------------------------- UTM --------------------------

		 // --------------------------- PMM --------------------------
	        projectXML.setControllerName(models.getControllerName());
	        projectXML.setControlActionName(models.getControlActionName());
	        projectXML.setOutputVariableName(models.getOutputName());
	        projectXML.setValueList(models.getValuelist());
		 // --------------------------- PMM --------------------------



	        m.marshal(projectXML, file);
	        setFilePath(file);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

}