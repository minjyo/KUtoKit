package kutokit;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kutokit.view.CseController;
import kutokit.view.PmmController;
import kutokit.view.CtmController;
import kutokit.view.UtmController;
import kutokit.view.RootLayoutController;

public class MainApp extends Application {
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	 
	@Override
	//auto execute after main execute
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Kutokit");

	        initRootLayout();

	        //showCseView();
	}

	 /**
     * init root layout
     */
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
            controller.setMainApp(this);
            
            System.out.println("a");
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

            // add scene in center of root layout 
            rootLayout.setCenter(View);
            
            //add controller
            UtmController controller = loader.getController();
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
}
