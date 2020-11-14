package kutokit.model.ctm;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CTMDataStore {

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