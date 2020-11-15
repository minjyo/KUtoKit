
package kutokit.model.ctm;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CTMDataStore {

<<<<<<< HEAD
	private String controller, controlAction;
	public int tableSize;

	ObservableList<CTM> CTMTableList = FXCollections.observableArrayList();

	public ObservableList<CTM> getCTMTableList() {
		tableSize = CTMTableList.size();
		return this.CTMTableList;
	}

	public String getController() {
		return this.controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getControlAction() {
		return this.controlAction;
	}

	public void setControlAction(String controlAction) {
		this.controlAction = controlAction;
	}
}
=======
	public String ctmController, ctmControlAction;
	public ArrayList<String> ctmCases = new ArrayList<String>();
	public ArrayList<String[]> ctmContexts = new ArrayList<String[]>();
	public ArrayList<String> ctmHazardous = new ArrayList<String>();
	public int rowSize;
	
	ObservableList<CTM> CTMTableList = FXCollections.observableArrayList();

	public ObservableList<CTM> getCTMTableList() {
		rowSize = CTMTableList.size();
		if(rowSize > 0) {
			ctmController = this.CTMTableList.get(0).getControllerName();
			ctmControlAction = this.CTMTableList.get(0).getControlAction();
			ArrayList<String> tempCases = new ArrayList<String>();
			ArrayList<String> tempHazardous = new ArrayList<String>();
			ArrayList<String[]> tempContexts = new ArrayList<String[]>();
			for(int i=0;i<rowSize;i++) {
				tempCases.add(this.CTMTableList.get(i).getCasesValue());
				tempHazardous.add(this.CTMTableList.get(i).getHazardousValue());
				tempContexts.add(this.CTMTableList.get(i).getContexts());
			}
			ctmCases = tempCases;
			ctmHazardous = tempHazardous;
			ctmContexts = tempContexts;
		}
		return this.CTMTableList;
	}
	


	public String getController() {
		return this.ctmController;
	}

	public void setController(String controller) {
		this.ctmController = controller;
	}

	public String getControlAction() {
		return this.ctmControlAction;
	}

	public void setControlAction(String controlAction) {
		this.ctmControlAction = controlAction;
	}
	
	public ArrayList<String> getCases() {
		return this.ctmCases;
	}

	public void setCases(ArrayList<String> cases) {
		this.ctmCases = cases;
	}
	
	public ArrayList<String[]> getContexts() {
		return this.ctmContexts;
	}

	public void setContexts(ArrayList<String[]> contexts) {
		this.ctmContexts = contexts;
	}
	
	public ArrayList<String> getHazardous() {
		return this.ctmHazardous;
	}

	public void setHazardous(ArrayList<String> hazardous) {
		this.ctmHazardous = hazardous;
	}
	
	
} 
>>>>>>> master
