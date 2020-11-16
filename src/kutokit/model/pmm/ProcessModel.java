package kutokit.model.pmm;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {
	//controller & control action related to this process model
	private String controllerName, controlActionName;
	
	//selected output variables
	private ObservableList<String> selectedOutputs = FXCollections.observableArrayList();

	//related variables & nodes for selected output
	private ObservableList<String> valueList = FXCollections.observableArrayList();

	/*
	 * default constructor
	 */
	public ProcessModel() {
	}

	public ProcessModel(String controller, String ca, ObservableList<String> selectedOutputs, ObservableList<String> valueList) {
		this.controllerName = controller;
		this.controlActionName = ca;
		this.selectedOutputs = selectedOutputs;
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
	
	public ObservableList<String> getSelectedOutputs(){
		return this.selectedOutputs;
	}
	
	public void setSelectedOutputs(ObservableList<String> selectedOutputs) {
		this.selectedOutputs = selectedOutputs;
	}

	public ObservableList<String> getValuelist() {
		return this.valueList;
	}

	public void setValuelist(ObservableList<String> valueList) {
		this.valueList = valueList;
	}
}
