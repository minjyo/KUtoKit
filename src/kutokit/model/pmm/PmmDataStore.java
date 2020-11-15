package kutokit.model.pmm;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PmmDataStore {
	//list for each process model tabs
	private ObservableList<ProcessModel> processModelList = FXCollections.observableArrayList();
	
	//total input variables & related nodes related to each output variables
	private ArrayList<ArrayList<String>> inputList = new ArrayList<ArrayList<String>>();
	
	//total output variables
	private ObservableList<String> outputList = FXCollections.observableArrayList();
	
	public ObservableList<ProcessModel> getProcessModel(){
		return processModelList;
	}
	
	public void setProcessModel(ObservableList<ProcessModel> processModels) {
		this.processModelList = processModels;
	}
	
	public void addProcessModel(ProcessModel pm) {
		this.processModelList.add(pm);
	}
	
	public ArrayList<ArrayList<String>> getInputList(){
		return this.inputList;
	}
	
	public void setInputList(ArrayList<ArrayList<String>> inputList) {
		this.inputList = inputList;
	}

	// Selected Output variables
	public ObservableList<String> getOutputList() {
		return this.outputList;
	}

	public void setOutputList(ObservableList<String> outputlist) {
		this.outputList = outputlist;
	}
}
