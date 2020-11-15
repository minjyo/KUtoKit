package kutokit.model.pmm;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {
	//controller & control action related to this process model
	private String controllerName, controlActionName;
	
	//selected output variables
	private ArrayList<String> selectedOutputs = new ArrayList<String>();
	
	//related variables & nodes for selected output
	private ObservableList<String> valueList = FXCollections.observableArrayList();

	/*
	 * default constructor
	 */
	public ProcessModel() {
	}

	public ProcessModel(String controller, String ca, ArrayList<String> selectedOutputs, ObservableList<String> valueList) {
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
	
	public ArrayList<String> getSelectedOutputs(){
		return this.selectedOutputs;
	}
	
	public void setSelectedOutputs(ArrayList<String> selectedOutputs) {
		this.selectedOutputs = selectedOutputs;
	}

	public ObservableList<String> getValuelist() {
		return this.valueList;
	}

	public void setValuelist(ObservableList<String> valueList) {
		this.valueList = valueList;
	}
}
