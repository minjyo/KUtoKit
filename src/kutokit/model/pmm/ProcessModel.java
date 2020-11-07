package kutokit.model.pmm;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {

	private ArrayList<String> controllerName = new ArrayList<String>();
	private ArrayList<String> controlActionNames = new ArrayList<String>();
	private ArrayList<String> allCA =  new ArrayList<String>();
	private ArrayList<String> outputNames = new ArrayList<String>();
	private ObservableList<String> allOutput =  FXCollections.observableArrayList();
	private ObservableList<String> valuelist = FXCollections.observableArrayList();
	private File filePath;

	public ProcessModel() {

	}

	// File path
	public File getFilePath() {
		return filePath;
	}

	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}

	// Controller
	public ArrayList<String> getControllerName() {
		return controllerName;
	}

	public void setControllerName(ArrayList<String> controllerName) {
		this.controllerName.addAll(controllerName);
	}

	// Control Action
	public ArrayList<String> getControlActionName() {
		return controlActionNames;
	}

	public void setControlActionName(ArrayList<String> arrayList) {
		this.controlActionNames.addAll(arrayList);
	}

	// Selected Output variables
	public ArrayList<String> getOutputNames() {
		return outputNames;
	}

	public void setOutputNames(ArrayList<String> outputVariables) {
		this.outputNames = outputVariables;
	}

	// All output variables
	public ObservableList<String> getAllOutput() {
		return allOutput;
	}

	public void setAllOutput(ObservableList<String> allOutput) {
		this.allOutput = allOutput;
	}

	// Value list
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
	}

	public void deleteValue(String value) {
		valuelist.remove(value);
	}

	// All control actions
	public ArrayList<String> getAllCA() {
		return allCA;
	}

	public void setAllCA(ArrayList<String> controlAction) {
		this.allCA = controlAction;
	}
}
