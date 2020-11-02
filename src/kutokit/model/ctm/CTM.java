package kutokit.model.ctm;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CTM {
	private StringProperty controlAction;
	private StringProperty cases;
	private IntegerProperty no;
	private ComboBox hazardous;

	private StringProperty[] contexts;

	public CTM(String controlAction, String cases, int no, String[] contexts, ObservableList hazardous) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.hazardous = new ComboBox(hazardous);
		
		this.contexts = new StringProperty[contexts.length];

		for(int i=0;i<contexts.length;i++) {
			this.contexts[i] = new SimpleStringProperty(contexts[i]);
		}
		
//		this.hazardous.setItems(FXCollections.observableArrayList("No select", "O", "X"));
	}
	
	public String getContext(int i) {
		return contexts[i].get();
	}
	
	public StringProperty getContextProperty(int i) {
		//System.out.println("property["+i+"]:"+test[i]);
		return contexts[i];
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
	
	public ComboBox getHazardous() {
		return hazardous;
	}
	
	public void setHazardous(ComboBox val) {
		this.hazardous = val;
	}
	
}