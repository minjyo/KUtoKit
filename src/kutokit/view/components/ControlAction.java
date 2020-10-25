package kutokit.view.components;

import java.util.ArrayList;

import kutokit.model.Components;
import kutokit.view.components.Controller;

public class ControlAction {

	double startX, startY, endX, endY;
	Controller controller, controlled;
	int id;
	ArrayList<String> CA;
	Components dataStore;
	
	public ControlAction(String controller, String controlled,  ArrayList<String> CA, int id, Components dataStore) {
		this.controller = dataStore.findController(controller);
		this.controlled = dataStore.findController(controlled);
		this.startX = this.controller.getX()+75;
		this.startY = this.controller.getY()+100;
		this.endX = this.controlled.getX()+75;
		this.endY = this.controlled.getY();
		this.id = id;
		this.CA = CA;
	}
	
	public double getStartX() {
		return startX;
	}
	
	public double getStartY() {
		return startY;
	}
	
	public double getEndX() {
		return endX;
	}
	
	public double getEndY() {
		return endY;
	}
	
	public int getController() {
		return controller.getId();
	}
	
	public int getControlled() {
		return controlled.getId();
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<String> getCA() {
		return CA;
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
	
	public void setCA(ArrayList<String> CA) {
		this.CA = CA;
	}
	
}
