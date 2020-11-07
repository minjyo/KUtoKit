package kutokit.model.utm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UCADataStore {

	private String controller;
	private String controllAction;
	public int size;

	ObservableList<UCA> UCATableList = FXCollections.observableArrayList();

	public ObservableList<UCA> getUCATableList() {
		size = UCATableList.size();
		return UCATableList;
	}
	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getControllAction() {
		return controllAction;
	}

	public void setControllAction(String controllAction) {
		this.controllAction = controllAction;
	}
}
