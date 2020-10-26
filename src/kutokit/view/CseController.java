package kutokit.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import java.util.Iterator;
import java.util.Map;

import kutokit.MainApp;
import kutokit.view.components.*;
import kutokit.view.popup.*;
import kutokit.model.Components;

public class CseController {

	private MainApp mainApp;
	private Stage mainStage;

	private Components dataStore;
	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	
	private ArrayList<ArrowView> arrows = new ArrayList<ArrowView>();

	private ContextMenu ControllerContextMenu;
	private MenuItem itemC1, itemC2, itemC3;
	private ContextMenu CAContextMenu;
	private MenuItem itemCA1, itemCA2;

	@FXML
	Group root = new Group();
	@FXML
	AnchorPane board = new AnchorPane();
	@FXML
	ImageView touch, component, ca, feedback, text;

	// constructor
	public CseController() {

	}

	private void initialize() {
		dataStore = mainApp.components;

		// draw board from data store
		controllers = dataStore.getControllers();
		for (Controller c : controllers) {
			DoubleProperty X = new SimpleDoubleProperty(c.getX());
		    DoubleProperty Y = new SimpleDoubleProperty(c.getY());
		    
			RectangleView r = new RectangleView(X, Y, c.getName(), c.getId());
			//Rectangle r = new Rectangle(150, 100, Color.DARKCYAN);
			//StackPane s = makeRectangle(r, c.getName(), c.getId());
				
			addController(r, c);
		}
		
		controlActions = dataStore.getControlActions();
		for (ControlAction ca : controlActions) {
			//ArrowView a = new ArrowView(ca.getStartX(), ca.getStartY(), ca.getEndX(), ca.getEndY(), ca.getId());
			//ArrowView a = new ArrowView(ca.getController(), ca.getControlled(), ca.getId());
			
			//arrows.add(a);
			
			dataStore.findController(ca.getControllerID()).addCA(ca.getId(), 1);
			dataStore.findController(ca.getControlledID()).addCA(ca.getId(), 0);
			
			//addControlAction(a);
		}

		// Add through click
		component.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("controller");
				event.consume();
			}
		});

		// Add through click
		ca.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("ca");
				event.consume();
			}
		});

		// Add through click
		feedback.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("feedback");
				event.consume();
			}
		});

		addControllerContextMenu();
		addCAContextMenu();

	}

	private void addPopUp(String component) {
		FXMLLoader loader = new FXMLLoader();
		switch (component) {
		case "controller":
			loader.setLocation(getClass().getResource("popup/ControllerPopUpView.fxml"));
			break;
		case "ca":
			loader.setLocation(getClass().getResource("popup/AddCAPopUpView.fxml"));
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

			// add component when popup closed
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					switch (component) {
					case "controller":
						//should recttangleView
						ControllerPopUpController pop = loader.getController();
						Controller c = new Controller(10, 10, pop.name, dataStore.curId);

						Rectangle r = new Rectangle(150, 100, Color.DARKCYAN);
						StackPane s = makeRectangle(r, c.getName(), c.getId());

						dataStore.addController(c);
						addController(s, c);
						break;
					case "ca":
						AddCAPopUpController pop2 = loader.getController();
						ControlAction ca = new ControlAction(pop2.controller, pop2.controlled, pop2.CA, dataStore.curId, dataStore);
						
//						DoubleProperty startX = new SimpleDoubleProperty(0);
//					    DoubleProperty startY = new SimpleDoubleProperty(0);
//					    DoubleProperty endX   = new SimpleDoubleProperty(0);
//					    DoubleProperty endY   = new SimpleDoubleProperty(0);
						DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
						RectangleView rect1 = null, rect2 = null;
					   // int id = Integer.parseInt(((Label) stack.getChildren().get(2)).getText());
						for(Node node: root.getChildren()) {
							if(Integer.parseInt(node.getId())==ca.getControllerID()) {
								startX = node.layoutXProperty();
								startY = node.layoutYProperty();
//								RectangleView rect = (RectangleView) node;
//								//System.out.println("rect: " + rect.id);
//								Label l = (Label) rect.getChildren().get(1);
//								if(l.getText()==ca.getController().getName()) {
//									System.out.println("controller: " + ca.getControllerID());
//									System.out.println("controller: " + rect.id);
//									rect1 = rect;
//									System.out.println("controller: " + rect1.id);
////									startX = rect.x;
////									startY = rect.y;
//								}else if(l.getText()==ca.getControlled().getName()) {
//									System.out.println("controlled: " + ca.getControlledID());
//									System.out.println("controller: " + rect.id);
//									rect2 = rect;
//									System.out.println("controller: " + rect2.id);
////									endX = rect.x;
////									endY = rect.y;
//								}	
							}else if(Integer.parseInt(node.getId())==ca.getControlledID()) {
								endX = node.layoutXProperty();
								endY = node.layoutYProperty();
							}
						}
						ArrowView a = new ArrowView( startX, startY, endX,  endY, ca.getId());
						//ArrowView a = new ArrowView(rect1.x, rect1.y, rect2.x, rect2.y, ca.getId());
						System.out.println("startX: " + startX);
						System.out.println("startY: " + startY);
						System.out.println("endX: " + endX);
						System.out.println("endY: " + endY);
						//ArrowView a = new ArrowView(ca.getStartX(), ca.getStartY(), ca.getEndX(), ca.getEndY(), ca.getId());
						//ArrowView a = new ArrowView(ca.getController(), ca.getControlled(), ca.getId());
						//arrows.add(a);
						controlActions.add(ca);
						dataStore.addControlAction(ca);
					//	System.out.println("ca: " + dataStore.getControlActions().size());
						
						dataStore.findController(pop2.controller).addCA(ca.getId(), 1);
						dataStore.findController(pop2.controlled).addCA(ca.getId(), 0);
						
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

	// add controller to board
	private void addController(StackPane s, Controller c) {
		//enableDrag(s);

		s.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				ControllerContextMenu.show(s, event.getScreenX(), event.getScreenY());
			}
		});

		s.setLayoutX(c.getX());
		s.setLayoutY(c.getY());
		s.setId(Integer.toString(c.getId()));

		root.getChildren().add(s);
		
	}

	private void addControlAction(ArrowView ca) {

		ca.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				CAContextMenu.show(ca, event.getScreenX(), event.getScreenY());
			}
		});

		root.getChildren().add(ca);
		root.getChildren().add(ca.arrowHead);
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

	private void modifyPopUp(ArrowView arrow) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/ModifyCAPopUpView.fxml"));
		Parent popUproot;

		try {
			popUproot = (Parent) loader.load();

			Scene scene = new Scene(popUproot);
			ModifyCAPopUpController pop = loader.getController();

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			// add ca with name when popup closed
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					dataStore.curCA.setCA(pop.CA);
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

//	static class Delta {
//		double x, y;
//	}
//
//	// make a node movable by dragging it around with the mouse.
//	private void enableDrag(final StackPane shape) {
//		final Delta dragDelta = new Delta();
//
//		shape.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				// record a delta distance for the drag and drop operation.
//				dragDelta.x = shape.getLayoutX() - mouseEvent.getX();
//				dragDelta.y = shape.getLayoutY() - mouseEvent.getY();
//				shape.getScene().setCursor(Cursor.MOVE);
//			}
//		});
//		shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				shape.getScene().setCursor(Cursor.HAND);
//			}
//		});
//		shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				shape.setLayoutX(mouseEvent.getX() + dragDelta.x);
//				shape.setLayoutY(mouseEvent.getY() + dragDelta.y);
//				int id = Integer.parseInt(((Label) shape.getChildren().get(2)).getText());
////				Map<Integer, Integer> cas = dataStore.findController(id).getCA();
////				for( int CAId : cas.keySet() ){
////					for(ArrowView arrow : arrows) {
////						if(CAId==arrow.getID()) {
////							 if(dataStore.findController(id).getCA().get(CAId)==1) {
////			                    	System.out.println("ca: " + CAId + "type: controller");
////								 arrow = new ArrowView(shape.getLayoutX()+75, shape.getLayoutY()+100, arrow.endX, arrow.endY, arrow.getID());
////								 
////			                    }else {
////			                    	System.out.println("ca: " + CAId + "type: controlled");
////			                     arrow = new ArrowView(arrow.startX, arrow.startY, shape.getLayoutX()+75, shape.getLayoutY(), arrow.getID());
////			                    }
////						}
////					}
////      
////                }
//				//this should be before change mode to drag smoothly
//				dataStore.moveController(id, shape.getLayoutX(), shape.getLayoutY());
//			}
//		});
//		shape.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if (!mouseEvent.isPrimaryButtonDown()) {
//					shape.getScene().setCursor(Cursor.HAND);
//				}
//			}
//		});
//		shape.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if (!mouseEvent.isPrimaryButtonDown()) {
//					shape.getScene().setCursor(Cursor.DEFAULT);
//				}
//			}
//		});
//	}

	public void addControllerContextMenu() {
		ControllerContextMenu = new ContextMenu();

		itemC1 = new MenuItem("Modfiy");
		itemC1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// modifyRectangle(getParentMenu().get)
				StackPane stack = (StackPane) itemC1.getParentPopup().getOwnerNode();
				modifyPopUp(stack);
			}
		});
		itemC2 = new MenuItem("Delete");
		itemC2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StackPane stack = (StackPane) itemC1.getParentPopup().getOwnerNode();
				int id = Integer.parseInt(((Label) stack.getChildren().get(2)).getText());
				dataStore.deleteController(id);

				for (Node c : root.getChildren()) {
					if (c.equals(stack)) {
						root.getChildren().remove(c);
						return;
					}
				}
			}
		});
		itemC3 = new MenuItem("Process Model");
		itemC3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});
		ControllerContextMenu.getItems().addAll(itemC1, itemC2, itemC3);
	}

	public void addCAContextMenu() {
		CAContextMenu = new ContextMenu();

		itemCA1 = new MenuItem("Modfiy");
		itemCA1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// modifyRectangle(getParentMenu().get)
				ArrowView arrow = (ArrowView) itemCA1.getParentPopup().getOwnerNode();
				dataStore.curCA = dataStore.findControlAction(arrow.getID());
				modifyPopUp(arrow);
			}
		});
		itemCA2 = new MenuItem("Delete");
		itemCA2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArrowView arrow = (ArrowView) itemCA1.getParentPopup().getOwnerNode();
				dataStore.deleteControlAction(arrow.getID());

				for (Node a : root.getChildren()) {
					if (a.equals(arrow)) {
						root.getChildren().remove(a);
						return;
					}
				}
			}
		});
		CAContextMenu.getItems().addAll(itemCA1, itemCA2);
	}

	// set MainApp
	public void setMainApp(MainApp mainApp, Stage mainStage) {
		this.mainApp = mainApp;
		this.mainStage = mainStage;

		this.initialize();
	}
}
