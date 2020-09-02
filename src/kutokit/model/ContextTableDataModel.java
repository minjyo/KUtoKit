package kutokit.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ContextTableDataModel {
	private SimpleStringProperty controlAction;
	private SimpleStringProperty cases;
	private SimpleIntegerProperty no;
	private SimpleStringProperty contexts;
	private ComboBox hazardous;
	

	public ContextTableDataModel(String controlAction, String cases, int no, String contexts) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.contexts = new SimpleStringProperty(contexts);
		this.hazardous = new ComboBox();
		this.hazardous.setItems(FXCollections.observableArrayList("No select", "O", "X"));
	}


	public String getControlAction() {
		return controlAction.get();
	}

	public String getCases() {
		return cases.get();
	}

	public int getNo() {
		return no.get();
	}

	public String getContexts() {
		return contexts.get();
	}

	public ComboBox getHazardous() {
		return hazardous;
	}
	
	public void setHazardous(ComboBox hazardous) {
		this.hazardous = hazardous;
	}
	
}
