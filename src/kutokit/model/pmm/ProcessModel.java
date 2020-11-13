package kutokit.model.pmm;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessModel {

	private ArrayList<String> controllerName = new ArrayList<String>();

	private ArrayList<ArrayList<String>> controlActionNames = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> allCA =  new ArrayList<ArrayList<String>>();

	private ArrayList<ArrayList<String>> outputNames =  new ArrayList<ArrayList<String>>();
	private ObservableList<String> allOutput =  FXCollections.observableArrayList();
	private ObservableList<ArrayList<String>> valuelist = FXCollections.observableArrayList();
	
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

	public ArrayList<ArrayList<String>> getControlActionNames() {
		return controlActionNames;
	}

	public void setControlActionNames(ArrayList<ArrayList<String>> arrayList) {
		this.controlActionNames = arrayList;
	}

	// Selected Output variables
	public ArrayList<ArrayList<String>> getOutputNames() {
		return outputNames;
	}

	public void setOutputNames(ArrayList<ArrayList<String>> outputVariables) {
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
	public ObservableList<ArrayList<String>> getValuelist() {
		return valuelist;
	}

	public void setValuelist(ObservableList<ArrayList<String>> lists) {
		this.valuelist = lists;
	}

	// ERROR 수정 중 
	public void addValuelist(ArrayList<String> list) {
		this.valuelist.add(list);
	}

	// ERROR 수정 중 
	public void modifyValue(String oldValue, String newValue) {
		for(ArrayList<String> value: valuelist) {
			for(String data : value) {
				if( oldValue.equals(data)) {
					value.set(value.indexOf(data), newValue);
				}	
			}
		}
	}
	
	// ERROR 수정 중 
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
	

	public boolean isEmpty(ArrayList<ArrayList<String>> arraylist) {
		boolean result = false;
		int cnt = 0;
		for(ArrayList<String> list : arraylist) {
			if(list == null || list.isEmpty()) {
				cnt++;
			}else {
				continue;
			}
		}

		if(cnt == arraylist.size()) {
			result = true;
		}
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
