package kutokit.model.pmm;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {
	//controller & control action related to this process model
	private String controllerName, controlActionName;
	
	//related variables & nodes for selected output
	private ObservableList<String> valueList = FXCollections.observableArrayList();

	/*
	 * default constructor
	 */
	public ProcessModel() {
	}

	public ProcessModel(String controller, String ca, ObservableList<String> valueList) {
		this.controllerName = controller;
		this.controlActionName = ca;
		this.valueList = valueList;
	}
	
	public String getControllerName() {
		return this.controllerName;
	}
	
	public void setControllerName(String controller) {
		this.controllerName = controller;
	}
	
	public String getControlActionName() {
		return this.controlActionName;
	}
	
	public void setControlActionName(String ca) {
		this.controlActionName = ca;
	}

	public ObservableList<String> getValuelist() {
		return this.valueList;
	}

	public void setValuelist(ObservableList<String> valueList) {
		this.valueList = valueList;
	}
}
