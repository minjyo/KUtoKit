package kutokit.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import kutokit.MainApp;
import kutokit.model.cse.Components;
import kutokit.model.cse.ControlAction;
import kutokit.model.cse.Controller;
import kutokit.model.cse.Feedback;
import kutokit.model.cse.Text;
import kutokit.view.components.*;
import kutokit.view.popup.cse.AddCAPopUpController;
import kutokit.view.popup.cse.AddFBPopUpController;
import kutokit.view.popup.cse.AddTextPopUpController;
import kutokit.view.popup.cse.ControllerPopUpController;
import kutokit.view.popup.cse.ModifyCAPopUpController;
import kutokit.view.popup.cse.ModifyFBPopUpController;

public class CseController {

	private MainApp mainApp;
	private Stage mainStage;

	private Components dataStore;
	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	private ArrayList<Text> texts = new ArrayList<Text>();

	private ContextMenu ControllerContextMenu;
	private MenuItem itemC1, itemC2, itemC3;
	private ContextMenu CAContextMenu;
	private MenuItem itemCA1, itemCA2;
	private ContextMenu FBContextMenu;
	private MenuItem itemFB1, itemFB2;
	private ContextMenu TextContextMenu;
	private MenuItem itemT1, itemT2;

	ControllerPopUpController AddCpop;
	AddCAPopUpController AddCApop;
	AddFBPopUpController AddFBpop;
	AddTextPopUpController AddTextpop;
	ModifyCAPopUpController ModifyCApop;
	ModifyFBPopUpController ModifyFBpop;
	
	@FXML
	Group root = new Group();
	@FXML
	ScrollPane board = new ScrollPane();
//	@FXML
//	ImageView component, ca, feedback, text;
	@FXML
	VBox componentBox, caBox, feedbackBox, textBox;

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
		    c.clearNum();
		    
			RectangleView r = new RectangleView(X, Y, c.getName(), c.getId(), dataStore);
			c.setRectangle(r);

			addController(r, c);
		}
		
		controlActions = dataStore.getControlActions();
		for (ControlAction ca : controlActions) {
			Controller controller = dataStore.findController(ca.getControllerID());
			Controller controlled = dataStore.findController(ca.getControlledID());
			
			ca.setController(controller);
			ca.setControlled(controlled);
			
			int[] startNum = controller.getNum();
			int[] endNum = controlled.getNum();
			
			DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
			for(Node node: root.getChildren()) {
				if(Integer.parseInt(node.getId())==ca.getControllerID()) {
					startX = node.layoutXProperty();
					startY = node.layoutYProperty();
					controller.resizeRectangle("ca");
				}else if(Integer.parseInt(node.getId())==ca.getControlledID()) {
					endX = node.layoutXProperty();
					endY = node.layoutYProperty();   
					controlled.resizeRectangle("ca");
				}
			}
			ArrowView a = new ArrowView(ca, startX, startY, endX,  endY, ca.getId(), startNum, endNum);
			a.toBack();
			LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ca.getCA(), "CA", endNum);
			a.setLabel(label);
			
			controller.addCA(ca.getId(), 1);
			controlled.addCA(ca.getId(), 0);
			
			addControlAction(a, label, ca);
		}
		
		feedbacks = dataStore.getFeedbacks();
		for (Feedback fb : feedbacks) {
			Controller controller = dataStore.findController(fb.getControllerID());
			Controller controlled = dataStore.findController(fb.getControlledID());
			
			fb.setController(controller);
			fb.setControlled(controlled);
			
			int[] startNum = controller.getNum();
			int[] endNum = controlled.getNum();
			
			DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
			for(Node node: root.getChildren()) {
				if(Integer.parseInt(node.getId())==fb.getControlledID()) {
					startX = node.layoutXProperty();
					startY = node.layoutYProperty();
					controlled.resizeRectangle("fb");
				}else if(Integer.parseInt(node.getId())==fb.getControllerID()) {
					endX = node.layoutXProperty();
					endY = node.layoutYProperty();
					controller.resizeRectangle("fb");
				}
			}
			ArrowView a = new ArrowView(fb, startX, startY, endX,  endY, fb.getId(), startNum, endNum);
			LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, fb.getFB(), "FB", endNum);
			a.setLabel(label);
	
			controller.addFB(fb.getId(), 1);
			controlled.addFB(fb.getId(), 0);
			
			addFeedback(a, label, fb);
		}
		
		texts = dataStore.getTexts();
		for (Text text : texts) {
			DoubleProperty X = new SimpleDoubleProperty(text.getX());
		    DoubleProperty Y = new SimpleDoubleProperty(text.getY());
		    
		    //System.out.println(X.get() + " " + Y.get());
		    
		    TextView t = new TextView(X, Y, text.getContent(), text.getId(), dataStore);

			addText(t, text);
		}

		// Add through click
		componentBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("controller");
				event.consume();
			}
		});

		// Add through click
		caBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("ca");
				event.consume();
			}
		});

		// Add through click
		feedbackBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("feedback");
				event.consume();
			}
		});
		
		// Add through click
		textBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addPopUp("text");
				event.consume();
			}
		});

		addControllerContextMenu();
		addCAContextMenu();
		addFBContextMenu();
		addTextContextMenu();
	}

	private void addPopUp(String component) {
		FXMLLoader loader = new FXMLLoader();
		
		switch (component) {
		case "controller":
			loader.setLocation(getClass().getResource("popup/cse/ControllerPopUpView.fxml"));
			AddCpop = loader.getController();
			break;
		case "ca":
			loader.setLocation(getClass().getResource("popup/cse/AddCAPopUpView.fxml"));
			AddCApop = loader.getController();
			break;
		case "feedback":
			loader.setLocation(getClass().getResource("popup/cse/AddFBPopUpView.fxml"));
			AddFBpop = loader.getController();
			break;
		case "text":
			loader.setLocation(getClass().getResource("popup/cse/AddTextPopUpView.fxml"));
			AddTextpop = loader.getController();
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
						AddCpop = loader.getController();
						
						if(AddCpop.OKclose) {
							Controller c = new Controller(10, 10, AddCpop.name, dataStore.curId);

							DoubleProperty X = new SimpleDoubleProperty(c.getX());
						    DoubleProperty Y = new SimpleDoubleProperty(c.getY());
						    
							RectangleView r = new RectangleView(X, Y, c.getName(), c.getId(), dataStore);
							c.setRectangle(r);

							addController(r, c);
							dataStore.addController(c);
						}
						break;
					case "ca":
						AddCApop = loader.getController();
						
						if(AddCApop.OKclose) {
							ControlAction ca = new ControlAction(AddCApop.controller, AddCApop.controlled, AddCApop.CA, dataStore.curId, dataStore);
							Controller controller = dataStore.findController(AddCApop.controller);
							Controller controlled = dataStore.findController(AddCApop.controlled);
							
							int[] startNum = controller.getNum();
							int[] endNum = controlled.getNum();
							
							DoubleProperty  startX = null, startY = null, endX = null,  endY = null;
							
							
							for(Node node: root.getChildren()) {
								if(Integer.parseInt(node.getId())==ca.getControllerID()) {
									startX = node.layoutXProperty();
									startY = node.layoutYProperty();
									controller.resizeRectangle("ca");
								}else if(Integer.parseInt(node.getId())==ca.getControlledID()) {
									endX = node.layoutXProperty();
									endY = node.layoutYProperty();
									controlled.resizeRectangle("ca");
								}
							}
							ArrowView a = new ArrowView(ca, startX, startY, endX,  endY, ca.getId(), startNum, endNum);
							LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ca.getCA(), "CA", endNum);
							a.setLabel(label);
							
							dataStore.addControlAction(ca);
							
							controller.addCA(ca.getId(), 1);
							controlled.addCA(ca.getId(), 0);
							
							addControlAction(a, label, ca);
						}
						break;
					case "feedback":
						AddFBpop = loader.getController();
						
						if(AddFBpop.OKclose) {
							Feedback fb = new Feedback(AddFBpop.controlled, AddFBpop.controller, AddFBpop.FB, dataStore.curId, dataStore);
							Controller controller = dataStore.findController(AddFBpop.controller);
							Controller controlled = dataStore.findController(AddFBpop.controlled);
							
							int[] startNum = controlled.getNum();
							int[] endNum = controller.getNum();
							
							DoubleProperty  startX1 = null, startY1 = null, endX1 = null,  endY1 = null;
							
							for(Node node: root.getChildren()) {
								//System.out.println(node.getId());
								if(Integer.parseInt(node.getId())==fb.getControlledID()) {
									startX1 = node.layoutXProperty();
									startY1 = node.layoutYProperty();
									controlled.resizeRectangle("fb");
								}else if(Integer.parseInt(node.getId())==fb.getControllerID()) {
									endX1 = node.layoutXProperty();
									endY1 = node.layoutYProperty();
									controller.resizeRectangle("fb");
								}
							}
							ArrowView a1 = new ArrowView(fb, startX1, startY1, endX1,  endY1, fb.getId(), startNum, endNum);
							LabelView label1 = new LabelView(a1.startX, a1.startY, a1.endX, a1.endY, fb.getFB(), "FB", endNum);
							a1.setLabel(label1);
							
							dataStore.addFeedback(fb);
							
							controller.addFB(fb.getId(), 1);
							controlled.addFB(fb.getId(), 0);
						
							addFeedback(a1, label1, fb);
						}
						break;
					case "text":
						AddTextpop = loader.getController();
						
						if(AddTextpop.OKclose) {
							Text text = new Text(50, 50, AddTextpop.content, dataStore.curId);

							DoubleProperty X = new SimpleDoubleProperty(text.getX());
						    DoubleProperty Y = new SimpleDoubleProperty(text.getY());
						    
							TextView t = new TextView(X, Y, text.getContent(), text.getId(), dataStore);
							
							addText(t, text);
							dataStore.addText(text);
						}
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
		//s.toFront();
	}

	private void addControlAction(Path s, Label l, ControlAction ca) {

		s.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				CAContextMenu.show(s, event.getScreenX(), event.getScreenY());
			}
		});

		s.setId(Integer.toString(ca.getId()));
		int labelID = - ca.getId();
		l.setId(Integer.toString(labelID));
		
		root.getChildren().addAll(s, l);
		s.toBack();
	}
	
	private void addFeedback(Path s, Label l, Feedback fb) {

		s.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				FBContextMenu.show(s, event.getScreenX(), event.getScreenY());
			}
		});

		s.setId(Integer.toString(fb.getId()));
		int labelID = - fb.getId();
		l.setId(Integer.toString(labelID));
		
		root.getChildren().addAll(s, l);
		s.toBack();
	}
	
	private void addText(TextView t, Text text) {

		t.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				TextContextMenu.show(t, event.getScreenX(), event.getScreenY());
			}
		});

		t.setId(Integer.toString(text.getId()));
		
		root.getChildren().addAll(t);
	}

	private void modifyControllerPopUp(RectangleView rect) {
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("popup/cse/ControllerPopUpView.fxml"));
		  Parent popUproot;
		  
		  try {
			  	popUproot = (Parent) loader.load();
				
				Scene scene = new Scene(popUproot);
				AddCpop = loader.getController();
				
				  Stage stage = new Stage();
				  stage.setScene(scene);
				  stage.show();
				  
				  //add controller with name when popup closed
				  stage.setOnHidden(new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	AddCpop = loader.getController();
					    	
					    	if(AddCpop.OKclose) {
					    		modifyRectangle(rect, AddCpop.name);
					    	}
					    }
					  });
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	}

	private void modifyControlActionPopUp(ArrowView a) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/cse/ModifyCAPopUpView.fxml"));
		Parent popUproot;

		try {
			popUproot = (Parent) loader.load();

			Scene scene = new Scene(popUproot);
			ModifyCApop = loader.getController();

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			// add ca with name when popup closed
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					ModifyCApop = loader.getController();
			    	
			    	if(ModifyCApop.OKclose) {
			    		dataStore.curCA.setCA(ModifyCApop.CA);
						for(Node node : root.getChildren()) {
							if(node.equals(a.label)) {
								root.getChildren().remove(node);
								break;
							}
						}
						LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ModifyCApop.CA, "CA", a.endNum);
						label.setId(a.getId());
						a.label = label;
						root.getChildren().add(label);
			    	}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void modifyFeedbackPopUp(ArrowView a) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("popup/cse/ModifyFBPopUpView.fxml"));
		Parent popUproot;

		try {
			popUproot = (Parent) loader.load();

			Scene scene = new Scene(popUproot);
			ModifyFBpop = loader.getController();

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			// add ca with name when popup closed
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					ModifyFBpop = loader.getController();
			    	
			    	if(ModifyFBpop.OKclose) {
			    		dataStore.curFB.setFB(ModifyFBpop.FB);
						for(Node node : root.getChildren()) {
							if(node.equals(a.label)) {
								root.getChildren().remove(node);
								break;
							}
						}
						LabelView label = new LabelView(a.startX, a.startY, a.endX, a.endY, ModifyFBpop.FB, "FB", a.endNum);
						label.setId(a.getId());
						a.label = label;
						root.getChildren().add(label);
			    	}
					
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void modifyTextPopUp(TextView text) {
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("popup/cse/AddTextPopUpView.fxml"));
		  Parent popUproot;
		  
		  try {
			  	popUproot = (Parent) loader.load();
				
				Scene scene = new Scene(popUproot);
				AddTextpop = loader.getController();
				
				  Stage stage = new Stage();
				  stage.setScene(scene);
				  stage.show();
				  
				  //add controller with name when popup closed
				  stage.setOnHidden(new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent e) {
					    	AddTextpop = loader.getController();
					    	
					    	if(AddTextpop.OKclose) {
					    		Text t = dataStore.findText(text.id);
					    		t.setContent(AddTextpop.content);
					    		for(Node node : root.getChildren()) {		
									if(node.equals(text)) {
										root.getChildren().remove(node);
										TextView text = new TextView(node.layoutXProperty(), node.layoutYProperty(), AddTextpop.content, Integer.parseInt(node.getId()), dataStore);
										addText(text, t);
										break;
									}
								}
								
					    	}
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
				
				for (Node c : root.getChildren()) {
					if (c.equals(rect)) {
						root.getChildren().remove(c);
						break;
					}
				}
				
				//delete ca
				Map<Integer, Integer> CAs = dataStore.findController(rect.id).getCA();
				for(int i=0; i<CAs.size(); i++) {
					for (Node ca : root.getChildren()) {
						if (CAs.containsKey(Integer.parseInt(ca.getId()))) {
							for(Node l : root.getChildren()) {
								if(Integer.parseInt(l.getId()) == -Integer.parseInt(ca.getId())){
									root.getChildren().remove(l);
									break;
								}
							}
							root.getChildren().remove(ca);
							break;
						}
					}
				}	
				
				//delete fb
				Map<Integer, Integer> FBs = dataStore.findController(rect.id).getFB();
				for(int i=0; i<CAs.size(); i++) {
					for (Node fb : root.getChildren()) {
						if (FBs.containsKey(Integer.parseInt(fb.getId()))) {
							for(Node l : root.getChildren()) {
								if(Integer.parseInt(l.getId()) == -Integer.parseInt(fb.getId())){
									root.getChildren().remove(l);
									break;
								}
							}
							root.getChildren().remove(fb);
							break;
						}
					}
				}
				
				dataStore.deleteController(rect.id); 
			}
		});
		itemC3 = new MenuItem("Process Model");
		itemC3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RectangleView rect = (RectangleView) itemC1.getParentPopup().getOwnerNode();
				dataStore.curController = dataStore.findController(rect.id);
				if(dataStore.curController.getCA().size()==0) {
					  FXMLLoader loader = new FXMLLoader();
					  loader.setLocation(getClass().getResource("popup/cse/ErrorNoCAFB.fxml"));
					  Parent popUproot;
					  try {
						  	popUproot = (Parent) loader.load();
							Scene scene = new Scene(popUproot);
							Stage stage = new Stage();
							stage.setScene(scene);
							stage.show();
					  }catch(IOException e) {
						  e.printStackTrace();
					  }  
				}else {
					mainApp.showPmmView();
				}
				
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
						root.getChildren().remove(arrow.label);
						root.getChildren().remove(a);
						break;
					}
				}
			}
		});
		CAContextMenu.getItems().addAll(itemCA1, itemCA2);
	}
	
	public void addFBContextMenu() {
		FBContextMenu = new ContextMenu();

		itemFB1 = new MenuItem("Modfiy");
		itemFB1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// modifyRectangle(getParentMenu().get)
				ArrowView arrow = (ArrowView) itemFB1.getParentPopup().getOwnerNode();
				dataStore.curFB = dataStore.findFeedback(arrow.getID());
				modifyFeedbackPopUp(arrow);
			}
		});
		itemFB2 = new MenuItem("Delete");
		itemFB2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArrowView arrow = (ArrowView) itemFB1.getParentPopup().getOwnerNode();
				dataStore.deleteFeedback(arrow.getID());
				
				for (Node a : root.getChildren()) {
					if (a.equals(arrow)) {
						root.getChildren().remove(arrow.label);
						root.getChildren().remove(a);
						break;
					}
				}
			}
		});
		FBContextMenu.getItems().addAll(itemFB1, itemFB2);
	}
	
	public void addTextContextMenu() {
		TextContextMenu = new ContextMenu();

		itemT1 = new MenuItem("Modfiy");
		itemT1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// modifyRectangle(getParentMenu().get)
				TextView text = (TextView) itemT1.getParentPopup().getOwnerNode();
				modifyTextPopUp(text);
			}
		});
		itemT2 = new MenuItem("Delete");
		itemT2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TextView text = (TextView) itemT1.getParentPopup().getOwnerNode();
				dataStore.deleteText(text.id);
				
				for (Node a : root.getChildren()) {
					if (a.equals(text)) {
						root.getChildren().remove(a);
						break;
					}
				}
			}
		});
		TextContextMenu.getItems().addAll(itemT1, itemT2);
	}

	// set MainApp
	public void setMainApp(MainApp mainApp, Stage mainStage) {
		this.mainApp = mainApp;
		this.mainStage = mainStage;

		this.initialize();
	}
}
