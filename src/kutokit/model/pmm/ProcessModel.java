package kutokit.model.pmm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {
	private String controllerName, controlActionName, outputName; 
	private ObservableList<String> valuelist = FXCollections.observableArrayList();
	
	public ProcessModel() {
		controllerName = "RPS";
		controlActionName = "Trip signal";
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
	
	public void addValuelist(String value) {
		this.valuelist.add(value);
	}
	
	public void modifyValue(String oldValue, String newValue) {
		for(String value: valuelist) {
			if( oldValue.equals(value)) {
				valuelist.set(valuelist.indexOf(value), newValue);
			}
		}
		/*
		 * if value is already existed, 
		 */
	}
	
	public void deleteValue(String value) {
		valuelist.remove(value);
	}
}
