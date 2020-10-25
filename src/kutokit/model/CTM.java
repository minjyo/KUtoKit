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
	private StringProperty controlAction;
	private StringProperty cases;
	private IntegerProperty no;
	private StringProperty contexts1;
	private StringProperty contexts2;
	private StringProperty contexts3;
	private StringProperty contexts4;
	private StringProperty contexts5;
	private StringProperty contexts6;
	private StringProperty contexts7;
	private StringProperty contexts8;
	private ComboBox<String> hazardous;
	

	public CTM(String controlAction, String cases, int no, String contexts1, String contexts2, String contexts3, String contexts4, String contexts5, String contexts6, String contexts7, String contexts8, ObservableList<String> hazardous) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.contexts1 = new SimpleStringProperty(contexts1);
		this.contexts2 = new SimpleStringProperty(contexts2);
		this.contexts3 = new SimpleStringProperty(contexts3);
		this.contexts4 = new SimpleStringProperty(contexts4);
		this.contexts5 = new SimpleStringProperty(contexts5);
		this.contexts6 = new SimpleStringProperty(contexts6);
		this.contexts7 = new SimpleStringProperty(contexts7);
		this.contexts8 = new SimpleStringProperty(contexts8);
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

	public String getContexts1() {
		return contexts1.get();
	}
	
	public StringProperty getContexts1Property() {
		return contexts1;
	}
	
	public String getContexts2() {
		return contexts2.get();
	}
	
	public StringProperty getContexts2Property() {
		return contexts2;
	}
	
	public String getContexts3() {
		return contexts3.get();
	}
	
	public StringProperty getContexts3Property() {
		return contexts3;
	}
	
	public String getContexts4() {
		return contexts4.get();
	}
	
	public StringProperty getContexts4Property() {
		return contexts4;
	}
	
	public String getContexts5() {
		return contexts5.get();
	}
	
	public StringProperty getContexts5Property() {
		return contexts7;
	}
	
	public String getContexts6() {
		return contexts6.get();
	}
	
	public StringProperty getContexts6Property() {
		return contexts6;
	}
	
	public String getContexts7() {
		return contexts7.get();
	}
	
	public StringProperty getContexts7Property() {
		return contexts7;
	}
	
	public String getContexts8() {
		return contexts8.get();
	}
	
	public StringProperty getContexts8Property() {
		return contexts8;
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