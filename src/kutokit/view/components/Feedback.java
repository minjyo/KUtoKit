package kutokit.view.components;

import java.util.ArrayList;

import kutokit.model.Components;
import kutokit.view.components.Controller;

public class Feedback {

	double startX, startY, endX, endY;
	Controller controller, controlled;
	Components dataStore;
	//xml
	int id;
	public int controllerID, controlledID;
	ArrayList<String> FB;
	
	
	public Feedback() {
		
	}
	
	public Feedback(String controller, String controlled,  ArrayList<String> FB, int id, Components dataStore) {
		this.controller = dataStore.findController(controller);
		this.controlled = dataStore.findController(controlled);
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
		return controller.getId();
	}
	
	public int getControlledID() {
		return controlled.getId();
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
