package kutokit.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();

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

			addController(r, c);
		}
		
		controlActions = dataStore.getControlActions();
		for (ControlAction ca : controlActions) {
			DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
			for(Node node: root.getChildren()) {
				if(Integer.parseInt(node.getId())==ca.getControllerID()) {
					startX = node.layoutXProperty();
					startY = node.layoutYProperty();
				}else if(Integer.parseInt(node.getId())==ca.getControlledID()) {
					endX = node.layoutXProperty();
					endY = node.layoutYProperty();
				}
			}
			ArrowView a = new ArrowView(ca, startX, startY, endX,  endY, ca.getId());
			LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ca.getCA());
			a.setLabel(label);
			
			dataStore.findController(ca.getControllerID()).addCA(ca.getId(), 1);
			dataStore.findController(ca.getControlledID()).addCA(ca.getId(), 0);
			
			addControlAction(a, label, ca);
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
						
						ControllerPopUpController pop = loader.getController();
						Controller c = new Controller(10, 10, pop.name, dataStore.curId);

						DoubleProperty X = new SimpleDoubleProperty(c.getX());
					    DoubleProperty Y = new SimpleDoubleProperty(c.getY());
					    
						RectangleView r = new RectangleView(X, Y, c.getName(), c.getId());

						addController(r, c);
						dataStore.addController(c);
						break;
					case "ca":
						AddCAPopUpController pop2 = loader.getController();
						ControlAction ca = new ControlAction(pop2.controller, pop2.controlled, pop2.CA, dataStore.curId, dataStore);
						
						DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
						
						for(Node node: root.getChildren()) {
							if(Integer.parseInt(node.getId())==ca.getControllerID()) {
								startX = node.layoutXProperty();
								startY = node.layoutYProperty();
							}else if(Integer.parseInt(node.getId())==ca.getControlledID()) {
								endX = node.layoutXProperty();
								endY = node.layoutYProperty();
							}
						}
						ArrowView a = new ArrowView(ca, startX, startY, endX,  endY, ca.getId());
						LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ca.getCA());
						a.setLabel(label);
						
						controlActions.add(ca);
						dataStore.addControlAction(ca);
						
						dataStore.findController(pop2.controller).addCA(ca.getId(), 1);
						dataStore.findController(pop2.controlled).addCA(ca.getId(), 0);
						
						addControlAction(a, label, ca);
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

	private void addControlAction(Path s, Label l, ControlAction ca) {

		s.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				CAContextMenu.show(s, event.getScreenX(), event.getScreenY());
			}
		});

		s.setId(Integer.toString(ca.getId()));
		
		root.getChildren().addAll(s, l);
	}

	private void modifyControllerPopUp(RectangleView rect) {
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
					    	modifyRectangle(rect, pop.name);
					    }
					  });
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	}

	private void modifyControlActionPopUp(ArrowView a) {
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
					for(Node node : root.getChildren()) {
						if(node.equals(a.label)) {
							root.getChildren().remove(node);
							break;
						}
					}
					LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, pop.CA);
					a.label = label;
					root.getChildren().add(label);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modifyRectangle(RectangleView rect, String name) {
		Label label = (Label) rect.getChildren().get(1);
		label.setText(name);

		//RectangleView rect =  (RectangleView) stack;
		//int id = Integer.parseInt(((Label) stack.getChildren().get(2)).getText());
		dataStore.modifyController(rect.id, name);
	}

	public void addControllerContextMenu() {
		ControllerContextMenu = new ContextMenu();

		itemC1 = new MenuItem("Modfiy");
		itemC1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// modifyRectangle(getParentMenu().get)
				RectangleView rect = (RectangleView) itemC1.getParentPopup().getOwnerNode();
				modifyControllerPopUp(rect);
			}
		});
		itemC2 = new MenuItem("Delete");
		itemC2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RectangleView rect = (RectangleView) itemC1.getParentPopup().getOwnerNode();
				dataStore.deleteController(rect.id);

				for (Node c : root.getChildren()) {
					if (c.equals(rect)) {
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
				modifyControlActionPopUp(arrow);
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
						break;
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
