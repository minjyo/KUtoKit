package kutokit.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kutokit.MainApp;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CseController {

	private MainApp mainApp;
	private Stage mainStage;
	
	@FXML
	Rectangle r1, r2;
	
	
	//constructor
	public CseController() {
		
		
	}

	static class Delta { double x, y; }
	
	private void initialize() {
		
		
		Group g = new Group();
		
		//Scene scene = new Scene(root, 400, 200);
		Scene scene =  mainStage.getScene();
	    //scene.setFill(Color.BLUE);
	    
	    //scene.setRoot(root);
	    
	   
//		Text source = new Text(50, 100, "drag");
//		Text target = new Text(250, 100, "drop");
		
		
//		
//		 r1.setOnDragDetected(new EventHandler <MouseEvent>() {
//	           
//	        });
		 
//		 
//		 r2.setOnDragOver(new EventHandler <DragEvent>() {
//	           
//	        });
//
//	        r2.setOnDragEntered(new EventHandler <DragEvent>() {
//	           
//	        });
//
//	        r2.setOnDragExited(new EventHandler <DragEvent>() {
//	            
//	        });
//	        
//	        r2.setOnDragDropped(new EventHandler <DragEvent>() {
//	            
//	        });
//
//	        r1.setOnDragDone(new EventHandler <DragEvent>() {
//	           
//	        });
	        
	     //   g.getChildren().add(r1);
	     //   g.getChildren().add(r2);
	        mainStage.setScene(scene);
	        mainStage.show();
	}
	
	@FXML
	public void setOnDragDetected() {
         /* drag was detected, start drag-and-drop gesture*/
         System.out.println("onDragDetected");
         
         /* allow any transfer mode */
         Dragboard db = r1.startDragAndDrop(TransferMode.ANY);
         
         /* put a string on dragboard */
         ClipboardContent content = new ClipboardContent();
         content.putString(r1.getId());
         db.setContent(content);
         r1.setFill(Color.BLACK);
         
	}
//	 
//	 public void setOnDragOver(DragEvent event,  Rectangle target) {
//         /* data is dragged over the target */
//         System.out.println("onDragOver");
//         
//         /* accept it only if it is  not dragged from the same node 
//          * and if it has a string data */
//         if (event.getGestureSource() != target &&
//                 event.getDragboard().hasString()) {
//             /* allow for both copying and moving, whatever user chooses */
//             event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//         }
//         
//         event.consume();
//     }
//	 
//	 public void setOnDragEntered(DragEvent event, Rectangle target) {
//         /* the drag-and-drop gesture entered the target */
//         System.out.println("onDragEntered");
//         /* show to the user that it is an actual gesture target */
//         if (event.getGestureSource() != target &&
//                 event.getDragboard().hasString()) {
//        	 System.out.println("onDragEntered");
//             target.setFill(Color.GREEN);
//         }
//         
//         event.consume();
//     }
//	 
//	 public void setOnDragExited(DragEvent event, Rectangle target) {
//         /* mouse moved away, remove the graphical cues */
//		 
//		 System.out.println("setOnDragExited");
//		 target.setFill(Color.RED);
//         event.consume();
//     }
//	 
//	 public void setOnDragDropped(DragEvent event, Rectangle target) {
//         /* data dropped */
//         System.out.println("onDragDropped");
//         /* if there is a string data on dragboard, read it and use it */
//         Dragboard db = event.getDragboard();
//         boolean success = false;
//         if (db.hasString()) {
//        	 System.out.println("onDragDropped");
//             //target.setText(db.getString());
//        	 target.setFill(Color.YELLOW);
//             success = true;
//         }
//         /* let the source know whether the string was successfully 
//          * transferred and used */
//         event.setDropCompleted(success);
//         
//         event.consume();
//     }
//	 
	 @FXML
	 public void setOnDragDone(DragEvent event) {
         /* the drag-and-drop gesture ended */
         System.out.println("onDragDone");
         /* if the data was successfully moved, clear it */
         if (event.getTransferMode() == TransferMode.MOVE) {
        	 System.out.println("onDragDone");
        	 r1.setFill(Color.YELLOW);
         }
         
         r2.setFill(Color.BLACK);
     }
	
	//set MainApp
	public void setMainApp(MainApp mainApp, Stage mainStage) {
		this.mainApp = mainApp;
		this.mainStage = mainStage;
		
		this.initialize();
	}
}
