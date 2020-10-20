package kutokit.view;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.awt.MouseInfo;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SubScene;
import java.awt.Point;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import kutokit.MainApp;
import kutokit.view.CompoPopupController;
import kutokit.view.components.*;

public class CseController {

	private MainApp mainApp;
	private Stage mainStage;
	
	
	@FXML
	Group root = new Group();
	@FXML
	AnchorPane board = new AnchorPane();
	@FXML
	ImageView touch, component, ca, feedback, text;
	
	String popUpValue = "none";
	
	//constructor
	public CseController() {
		
		
	}
	
	private void initialize() {
		
		//Scene scene =  mainStage.getScene();
		
//		Circle r = new Circle(5,5,5);
//		enableDrag(r);
//		
//		root = new Group(r);
//		board = new Scene(root);
		//scene.setRoot(root);
		
//		root = new Group();
//		Scene board = new Scene(root, 300, 300, Color.BLACK);

//		Rectangle r = new Rectangle(25,25,250,250);
//		r.setFill(Color.BLUE);
//
//		root.getChildren().add(r);
		
		//Add through click
		component.setOnMouseClicked(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              System.out.println("Add");
	              
	              popUp();
	              
	              
//	              if(!popUpValue.equals("Controller Name")) {
//	            	  System.out.println("name: " + popUpValue);
//	            	  Rectangle r = new Rectangle(100,50);
//		              enableDrag(r);
//		    
//		              root.getChildren().add(r);
//	              } 
	              event.consume();
	          }
	      });
		
		//Add through click
		ca.setOnMouseClicked(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              System.out.println("Add");
	              
	              Arc r = new Arc(100,50, 0, 0, 0, 0);
	              enableDrag(r);
	    
	              root.getChildren().add(r);
	              
	              event.consume();
	          }
	      });
		
		//Add through drag&drop
//		component.setOnDragDetected(new EventHandler <MouseEvent>() {
//	          public void handle(MouseEvent event) {
//	              /* drag was detected, start drag-and-drop gesture*/
//	              System.out.println("onDragDetected");
//	              
//	              /* allow any transfer mode */
//	              Dragboard db = component.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
//	              ClipboardContent content = new ClipboardContent();
//	              content.putString("component");
//	              db.setContent(content);
//	              db.setDragView(new Image("assets/component.png", 100, 40, false, false));
//	             
//	              event.consume();
//	          }
//	      });
//		
//		component.setOnDragDone(new EventHandler <DragEvent>() {
//	            public void handle(DragEvent event) {
//	            	
//	                /* the drag-and-drop gesture ended */
//	                System.out.println("onDragDone");
		
//					 Circle r = new Circle(5,5,5);
//			         enableDrag(r);
//			
//			         root.getChildren().add(r);
//			         
//			         event.consume();
//	           
//	                
//	                event.consume();
//	            }
//	        });
		
	}
	
	
	
	private String popUp() {
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("CompoPopup.fxml"));
	  Parent popUproot;
	  
	  try {
		  	popUproot = (Parent) loader.load();
			
			Scene scene = new Scene(popUproot);
			CompoPopupController pop = loader.getController();
			
			  Stage stage = new Stage();
			  stage.setScene(scene);
			  stage.show();
			  
			  //add controller with name when popup closed
			  stage.setOnHidden(new EventHandler<WindowEvent>() {
				    @Override
				    public void handle(WindowEvent e) {

				    	popUpValue = pop.name;
				    	
				    	System.out.println("name: " + popUpValue);
		            	Rectangle r = new Rectangle(150, 100, Color.DARKCYAN);
		            	
			            enableDrag(r);
			    
			            root.getChildren().add(r);
				    }
				  });
			  return popUpValue;
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "error";
	  }
	}
	
	static class Delta { double x, y; }
	// make a node movable by dragging it around with the mouse.
	private void enableDrag(final Shape shape) {
		final Delta dragDelta = new Delta();
		
		shape.setOnMousePressed(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
		    // record a delta distance for the drag and drop operation.
		    dragDelta.x = shape.getLayoutX() - mouseEvent.getX();
		    dragDelta.y = shape.getLayoutY() - mouseEvent.getY();
		    shape.getScene().setCursor(Cursor.MOVE);
		  }
		});
		shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  shape.getScene().setCursor(Cursor.HAND);
		  }
		});
		shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  shape.setLayoutX(mouseEvent.getX() + dragDelta.x);
			  shape.setLayoutY(mouseEvent.getY() + dragDelta.y);
		  }
		});
		shape.setOnMouseEntered(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
		    if (!mouseEvent.isPrimaryButtonDown()) {
		    	shape.getScene().setCursor(Cursor.HAND);
		    }
		  }
		});
		shape.setOnMouseExited(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
		    if (!mouseEvent.isPrimaryButtonDown()) {
		    	shape.getScene().setCursor(Cursor.DEFAULT);
		    }
		  }
		});
	}
	
	
	//set MainApp
	public void setMainApp(MainApp mainApp, Stage mainStage) {
		this.mainApp = mainApp;
		this.mainStage = mainStage;
		
		this.initialize();
	}
}
