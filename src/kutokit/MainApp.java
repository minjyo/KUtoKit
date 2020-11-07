package kutokit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import kutokit.view.LsController;
import kutokit.view.UtmController;
import kutokit.view.RootLayoutController;
import kutokit.model.ProjectXML;
import kutokit.model.cse.Components;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LhcDataStore;
import kutokit.model.ls.LSDataStore;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;

public class MainApp extends Application {

	 private Stage primaryStage;
	 private BorderPane rootLayout;

	 public static Components components;
	 public static LhcDataStore lhcDataStore;
	 public ProcessModel models;
	 public static ObservableList<UCADataStore> ucaDataStoreList = FXCollections.observableArrayList();
	 public static ObservableList<UCA> ucadatastore = FXCollections.observableArrayList();
	 public static CTMDataStore ctmDataStore;
	 public static LSDataStore lsDataStore;

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
		lhcDataStore = new LhcDataStore();
		models = new ProcessModel();
//change
//		ucadatastore = new UCADataStore();

		ctmDataStore = new CTMDataStore();
		lsDataStore = new LSDataStore();
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
            CtmController controller = loader.getController();
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
        	//Open when CTM data isn't null
        	if(ctmDataStore.getCTMTableList().isEmpty()){
    	        Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Caution");
    	        alert.setContentText("Please import CTM data before access UTM");
    	        alert.show();
        		return;
        	}

            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UtmView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            rootLayout.setCenter(View);

            //add controller
            UtmController controller = loader.getController();
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

    /*
     * called when dashboard button clicked
     */
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

	/*
	 * called when loss scenario button clicked
	 */
	public void showLsView() {
		try {
            // get maker scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LsView.fxml"));
            AnchorPane View = (AnchorPane) loader.load();

            // add scene in center of root layout
            rootLayout.setCenter(View);

            //add controller
            LsController controller = loader.getController();
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
		        JAXBContext context = JAXBContext
		                .newInstance(ProjectXML.class);
		        Unmarshaller um = context.createUnmarshaller();

		        ProjectXML projectXML = (ProjectXML) um.unmarshal(file);

		     // --------------------------- LHC --------------------------
		        lhcDataStore.getLossTableList().addAll(projectXML.getLossList());
		        lhcDataStore.getHazardTableList().addAll(projectXML.getHazardList());
		        lhcDataStore.getConstraintTableList().addAll(projectXML.getConstraintList());
			 // --------------------------- LHC --------------------------

		     // --------------------------- CSE --------------------------
		        components.getControllers().addAll(projectXML.getControllers());
		        components.getControlActions().addAll(projectXML.getControlActions());
		        components.getFeedbacks().addAll(projectXML.getFeedbacks());
		     // --------------------------- CSE --------------------------


		     // --------------------------- UTM --------------------------
		        ucadatastore.addAll(projectXML.getUCA());
		        ucaDataStoreList.addAll(projectXML.getUCADataStoreList());
		        int i=0;
		        for(UCADataStore u : ucaDataStoreList){
		        	for(int j=0;j<u.size;j++){
		        		u.getUCATableList().add(ucadatastore.get(i));
		        		i++;
		        	}
		        }
		      //--------------------------- UTM --------------------------

			 // --------------------------- PMM --------------------------
		        models.setControllerName(projectXML.getControllerName());;
		        models.setControlActionName(projectXML.getControlActionName());
		        models.setOutputNames(projectXML.getOutputVariableName());
		        models.setAllCA(projectXML.getAllCA());
		        models.setAllOutput(projectXML.getAllOutput());
		        models.getValuelist().addAll(projectXML.getValueList());
			 // --------------------------- PMM --------------------------


			 // --------------------------- CTM --------------------------
		        ctmDataStore.getCTMTableList().addAll(projectXML.getCTMList());
   	         // --------------------------- CTM --------------------------


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

	     // --------------------------- LHC --------------------------
	        projectXML.setLossList(lhcDataStore.getLossTableList());
	        projectXML.setHazardList(lhcDataStore.getHazardTableList());
	        projectXML.setConstraintList(lhcDataStore.getConstraintTableList());
	     // --------------------------- LHC --------------------------

	     // --------------------------- CSE --------------------------
	        projectXML.setControllers(components.getControllers());
	        projectXML.setControlActions(components.getControlActions());
	        projectXML.setFeedbacks(components.getFeedbacks());
	     // --------------------------- CSE --------------------------


	     // --------------------------- UTM --------------------------
	        projectXML.setUCA(ucadatastore);
	        projectXML.setUCAList(ucaDataStoreList);
	     // --------------------------- UTM --------------------------

		 // --------------------------- PMM --------------------------
	        projectXML.setControllerName(models.getControllerName());
	        projectXML.setControlActionName(models.getControlActionName());
	        projectXML.setOutputVariableName(models.getOutputNames());
	        projectXML.setAllCA(models.getAllCA());
	        projectXML.setAllOutput(models.getAllOutput());
	        projectXML.setValueList(models.getValuelist());
		 // --------------------------- PMM --------------------------

		 // --------------------------- CTM --------------------------
	        projectXML.setCTMList(ctmDataStore.getCTMTableList());
	     // --------------------------- CTM --------------------------

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