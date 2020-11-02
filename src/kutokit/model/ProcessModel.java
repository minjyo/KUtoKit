package kutokit.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {
	String controllerName, controlActionName, outputName; 
	ObservableList<String> valuelist = FXCollections.observableArrayList();
	
	public ProcessModel() {
		controllerName = "RPS";
		controlActionName = "Trip signal";
		outputName = "f_LO_SG1_LEVEL_Trip_Out";
		valuelist.addAll("first","second","third");
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControlActionName() {
		return controlActionName;
	}

	public void setControlActionName(String controlActionName) {
		this.controlActionName = controlActionName;
	}

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public ObservableList<String> getValuelist() {
		return valuelist;
	}

	public void setValuelist(ObservableList<String> valuelist) {
		this.valuelist = valuelist;
	}
	
	
	
}
