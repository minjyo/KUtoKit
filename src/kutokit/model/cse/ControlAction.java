package kutokit.model.cse;

import java.util.ArrayList;

public class ControlAction {

	double startX, startY, endX, endY;
	Controller controller, controlled;
	Components dataStore;
	//xml
	int id;
	ArrayList<String> CA;
	public int controllerID, controlledID;
	
	public ControlAction() {
		
	}
	
	public ControlAction(String controller, String controlled,  ArrayList<String> CA, int id, Components dataStore) {
		this.controller = dataStore.findController(controller);
		this.controlled = dataStore.findController(controlled);
		this.controllerID = this.controller.getId();
		this.controlledID = this.controlled.getId();
		this.id = id;
		this.CA = CA;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public Controller getControlled() {
		return controlled;
	}
	
	public int getControllerID() {
		return controllerID;
	}
	
	public int getControlledID() {
		return controlledID;
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<String> getCA() {
		return CA;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setControlled(Controller controlled) {
		this.controlled = controlled;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCA(ArrayList<String> CA) {
		this.CA = CA;
	}
	
}
