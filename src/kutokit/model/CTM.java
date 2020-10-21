package kutokit.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CTM {
	private SimpleStringProperty controlAction;
	private SimpleStringProperty cases;
	private SimpleIntegerProperty no;
	private SimpleStringProperty contexts;
	private ComboBox<String> hazardous;
	

	public CTM(String controlAction, String cases, int no, String contexts, ObservableList<String> hazardous) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.contexts = new SimpleStringProperty(contexts);
		this.hazardous = new ComboBox<String>(hazardous);
		//this.hazardous = new SimpleStringProperty(hazardous);
//		this.hazardous.setItems(FXCollections.observableArrayList("No select", "O", "X"));
	}


	public String getControlAction() {
		return controlAction.get();
	}
	
	public StringProperty getControlActionProperty() {
		return controlAction;
	}

	public String getCases() {
		return cases.get();
	}
	
	public StringProperty getCasesProperty() {
		return cases;
	}


	public int getNo() {
		return no.get();
	}
	
	public IntegerProperty getNoProperty() {
		return no;
	}

	public String getContexts() {
		return contexts.get();
	}
	
	public StringProperty getContextsProperty() {
		return contexts;
	}
	
	public ComboBox<String> getHazardousProperty() {
		return hazardous;
	}
	
	public void setHazardous(ComboBox<String> hazardous) {
		this.hazardous = hazardous;
	}

	/*
	public String getHazardous() {
		return hazardous.get();
	}
	
	public StringProperty getHazardousProperty() {
		return hazardous;
	}
	
	public void setHazardous(String hazardous) {
		this.hazardous.set(hazardous);
	}*/
	
}
