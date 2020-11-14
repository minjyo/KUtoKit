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
	
	public String getCAName() {
		return this.controlActionName;
	}
	
	public void setCANAme(String ca) {
		this.controlActionName = ca;
	}

	
	// Value list
	public ObservableList<String> getValuelist() {
		return this.valueList;
	}

	public void setValuelist(ObservableList<String> valueList) {
		this.valueList = valueList;
	}

	//	public ArrayList<String> modifyValue(int curIndex, String oldValue, String newValue) {
//
//		ArrayList<String> curList = valuelist.get(curIndex);
//				for(String data : curList) {
//					if( oldValue.equals(data)) {
//						curList.set(curList.indexOf(data), newValue);
//						valuelist.set(curIndex, curList);
//					}
//				}
//		return curList;
//	}
//	
//	public ArrayList<String> deleteValue(int curIndex, String oldValue) {
//		ArrayList<String> curList = valuelist.get(curIndex);
//			for(String data : curList) {
//				if(oldValue.equals(data)) {
//					curList.remove(curList.indexOf(data));
//					valuelist.set(curIndex, curList);
//				}
//			}
//		return curList;
//	}
//
//	// All control actions
//	public ArrayList<ArrayList<String>> getAllCA() {
//		return allCA;
//	}
//
//	public void setAllCA(ArrayList<ArrayList<String>> controlAction) {
//		this.allCA = controlAction;
//	}
//	
//
//	public boolean isEmpty(ArrayList<ArrayList<String>> arraylist) {
//		boolean result = false;
//		int cnt = 0;
//		for(ArrayList<String> list : arraylist) {
//			if(list == null || list.isEmpty()) {
//				cnt++;
//			}else {
//				continue;
//			}
//		}
//
//		if(cnt == arraylist.size()) {
//			result = true;
//		}
//		return result;
//	}
//	
//	public int getSize(ArrayList<ArrayList<String>> arraylist) {
//		int cnt=0;
//		for(ArrayList<String> list: arraylist) {
//			if(!isEmpty(arraylist)) {
//				cnt++;
//			}	
//		}
//		return cnt;
//	}
}
