package kutokit.model.pmm;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {

	private ArrayList<String> controllerName = new ArrayList<String>();

	private ArrayList<ArrayList<String>> controlActionNames = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> allCA =  new ArrayList<ArrayList<String>>();
	private ArrayList<String>[] outputNames = new ArrayList[10];

	private ObservableList<String> allOutput =  FXCollections.observableArrayList();
	private ObservableList<String> valuelist = FXCollections.observableArrayList();
	private ArrayList<String> valuelists = new ArrayList<String>();
	
	private File filePath;

	public ProcessModel() {
		for(int i=0; i<10; i++) {
			
			outputNames[i] = new ArrayList<String>();
		}
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
	public ArrayList<ArrayList<String>> getControlActionName() {
		return controlActionNames;
	}

	public void setControlActionName(ArrayList<ArrayList<String>> selectedCA) {

		this.controlActionNames = selectedCA;
	}

	// Selected Output variables
	public ArrayList<String>[] getOutputNames() {
		return outputNames;
	}

	public void setOutputNames(ArrayList<String>[] outputVariables) {
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
		for(String data : valuelist) {
			valuelists.add(data);
		}
		this.valuelist.addAll(valuelists);
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
	public ArrayList<ArrayList<String>> getAllCA() {
		return allCA;
	}

	public void setAllCA(ArrayList<ArrayList<String>> controlAction) {
		this.allCA = controlAction;
	}
	
	public boolean isEmpty(ArrayList<ArrayList<String>> arrayList) {
		boolean result = false;
		int cnt = 0;
		for(ArrayList<String> list : arrayList) {
			if(list == null || list.isEmpty()) {
				cnt++;
			}
		}
		if(cnt == arrayList.size()) result = true;
		return result;
	}
	
	public int getSize(ArrayList<ArrayList<String>> arraylist) {
		int cnt=0;
		for(ArrayList<String> list: arraylist) {
			if(!isEmpty(arraylist)) {
				cnt++;
			}	
		}
		return cnt;
	}
}
