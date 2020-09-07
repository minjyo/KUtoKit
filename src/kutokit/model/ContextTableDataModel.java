package kutokit.model;

import javafx.beans.property.SimpleBooleanProperty;
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
	private SimpleBooleanProperty hazardous;
	

	public ContextTableDataModel(String controlAction, String cases, int no, String contexts) {
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

	public String getCases() {
		return cases.get();
	}

	public int getNo() {
		return no.get();
	}

	public String getContexts() {
		return contexts.get();
	}

	public boolean getHazardous() {
		return hazardous.get();
	}
	
	public void setHazardous(boolean hazardous) {
		this.hazardous.set(hazardous);
	}
	
}
