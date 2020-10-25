package kutokit.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;


import kutokit.MainApp;
import kutokit.view.components.*;
import kutokit.view.popup.*;
import kutokit.model.Components;

public class CseController {

	private MainApp mainApp;
	private Stage mainStage;
	
	private Components dataStore;
	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	
	private ContextMenu contextMenu;
	private MenuItem item1, item2, item3;
	
	@FXML
	Group root = new Group();
	@FXML
	AnchorPane board = new AnchorPane();
	@FXML
	ImageView touch, component, ca, feedback, text;
		
	//constructor
	public CseController() {
		
	}
	
	private void initialize() {
		dataStore = mainApp.components;
		
		//draw board from data store
		controllers = dataStore.getControllers();
		for(Controller c : controllers) {
	    	Rectangle r = new Rectangle(150, 100, Color.DARKCYAN);
	    	StackPane s = makeRectangle(r, c.getName(), c.getId());
	    	
	    	addController(s, c);
		}
		
		//Add through click
		component.setOnMouseClicked(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	        	  addPopUp("controller");
	              event.consume();
	          }
	    });
		
		//Add through click
		ca.setOnMouseClicked(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	        	  addPopUp("ca");
	              event.consume();
	          }
		});
		
		//Add through click
		feedback.setOnMouseClicked(new EventHandler <MouseEvent>() {
		      public void handle(MouseEvent event) {
		       	  addPopUp("feedback");
		          event.consume();
		      }
		});
		
		contextMenu = new ContextMenu();
		
		item1 = new MenuItem("Modfiy");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            //	modifyRectangle(getParentMenu().get)
            	StackPane stack = (StackPane) item1.getParentPopup().getOwnerNode();
            	modifyPopUp(stack);
            }
        });
        item2 = new MenuItem("Delete");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	StackPane stack = (StackPane) item1.getParentPopup().getOwnerNode();
            	int id = Integer.parseInt(((Label) stack.getChildren().get(2)).getText());
            	dataStore.deleteController(id);
            	
            	for(Node c : root.getChildren()) {
            		if(c.equals(stack)){
            			root.getChildren().remove(c);
            			return;
            		}
            	}
            }
        });
        item3 = new MenuItem("Process Model");
        item3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        contextMenu.getItems().addAll(item1, item2, item3);
	}
	
	private void addPopUp(String component) {
	  FXMLLoader loader = new FXMLLoader();
	  switch(component) {
		  case "controller":
			  loader.setLocation(getClass().getResource("popup/ControllerPopUpView.fxml"));
			  break;
		  case "ca":
			  loader.setLocation(getClass().getResource("popup/CAPopUpView.fxml"));
			  break;
		  case "feedback":
			  loader.setLocation(getClass().getResource("popup/FeedbackPopUpView.fxml"));
			  break;
	  }
	  
	  Parent popUproot;
	  
	  try {
		  	popUproot = (Parent) loader.load();
			
			Scene scene = new Scene(popUproot);
			
			  Stage stage = new Stage();
			  stage.setScene(scene);
			  stage.show();
			  
			  //add component when popup closed
			  stage.setOnHidden(new EventHandler<WindowEvent>() {
				    @Override
				    public void handle(WindowEvent e) {
				    	switch(component) {
					    	case "controller":
					    		ControllerPopUpController pop = loader.getController();
					    		Controller c = new Controller(10, 10, pop.name, dataStore.curId);
						    	
						    	Rectangle r = new Rectangle(150, 100, Color.DARKCYAN);
						    	StackPane s = makeRectangle(r, c.getName(), c.getId());
						    	
						    	dataStore.addController(c);
						    	addController(s, c);
						    	break;
					    	case "ca":
					    		CAPopUpController pop2 = loader.getController();
					    		ControlAction ca = new ControlAction(pop2.controller, pop2.controlled, dataStore.curId, dataStore);
					    		
					    		ArrowView a = new ArrowView(ca.getStartX(), ca.getStartY(), ca.getEndX(), ca.getEndY());
					    		
					    		dataStore.addControlAction(ca);
					    		addControlAction(a);
					    		break;
					    	case "feedback":
				    		break;
				    	}
				    	
				    }
				  });
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	}
	
	//add controller to board
	private void addController(StackPane s, Controller c) {
		enableDrag(s);
		
		s.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
            	
                contextMenu.show(s, event.getScreenX(), event.getScreenY());
            }
        });
		
        s.setLayoutX(c.getX());
        s.setLayoutY(c.getY());
        
        root.getChildren().add(s);
	}
	
	private void addControlAction(ArrowView ca) {
		
		ca.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
            	
                contextMenu.show(ca, event.getScreenX(), event.getScreenY());
            }
        });
        
        root.getChildren().add(ca);
	}
	
	private StackPane makeRectangle(Shape shape, String name, int id) {
		StackPane stack = new StackPane();
		Label idLabel = new Label(Integer.toString(id));
		idLabel.setVisible(false);
		
	    stack.getChildren().addAll(shape, new Label(name), idLabel);
	    
	    return stack;
	}
	
	private void modifyPopUp(StackPane stack) {
		 FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("popup/ControllerPopUpView.fxml"));
		  Parent popUproot;
		  
		  try {
			  	popUproot = (Parent) loader.load();
				
				Scene scene = new Scene(popUproot);
				ControllerPopUpController pop = loader.getController();
				
				  Stage stage = new Stage();
				  stage.setScene(scene);
				  stage.show();
				  
				  //add controller with name when popup closed
				  stage.setOnHidden(new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	modifyRectangle(stack, pop.name);
					    }
					  });
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	}
	
	private void modifyRectangle(StackPane stack, String name) {
		Label label = (Label) stack.getChildren().get(1);
		label.setText(name);
		
		int id = Integer.parseInt(((Label) stack.getChildren().get(2)).getText());
    	dataStore.modifyController(id, name);
	}
	
	static class Delta { double x, y; }
	// make a node movable by dragging it around with the mouse.
	private void enableDrag(final StackPane shape) {
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
			  int id = Integer.parseInt(((Label) shape.getChildren().get(2)).getText());
			  dataStore.moveComponent(id, shape.getLayoutX(), shape.getLayoutY());
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
