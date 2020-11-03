package kutokit.model.cse;

import java.util.ArrayList;

public class Feedback {

	double startX, startY, endX, endY;
	Controller controller, controlled;
	Components dataStore;
	// xml
	int id;
	public int controllerID, controlledID;
	ArrayList<String> FB;

	public Feedback() {

	}

	public Feedback(String controller, String controlled, ArrayList<String> FB, int id, Components dataStore) {
		this.controller = dataStore.findController(controller);
		this.controlled = dataStore.findController(controlled);
		this.controllerID = this.controller.getId();
		this.controlledID = this.controlled.getId();
		this.id = id;
		this.FB = FB;
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

	public ArrayList<String> getFB() {
		return FB;
	}

	public void setController(String controller) {
		this.controller = dataStore.findController(controller);
	}

	public void setControlled(String controlled) {
		this.controlled = dataStore.findController(controlled);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFB(ArrayList<String> FB) {
		this.FB = FB;
	}

}
