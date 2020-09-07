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
	private SimpleBooleanProperty hazardous;
	

	public CTM(String controlAction, String cases, int no, String contexts) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.contexts = new SimpleStringProperty(contexts);
		this.hazardous = new SimpleBooleanProperty(false);
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

	public boolean getHazardous() {
		return hazardous.get();
	}
	
	public BooleanProperty getHazardousProperty() {
		return hazardous;
	}
	
	public void setHazardous(boolean hazardous) {
		this.hazardous.set(hazardous);
	}
	
}
