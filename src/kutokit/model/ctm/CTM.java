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
	private ComboBox<String> hazardous;
	private StringProperty[] contexts;

	public String ControlAction;
	public String Cases;
	public int No;
	public String Hazardous;
	public String[] Contexts;

	public CTM(){

	}

	public CTM(String controlAction, String cases, int no, String[] contexts, ComboBox<String> hazardous) {
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = new SimpleStringProperty(cases);
		this.no = new SimpleIntegerProperty(no);
		this.hazardous = hazardous;
		hazardous.setValue("X");
		hazardous.setOnAction(event ->{
			this.Hazardous = hazardous.getSelectionModel().getSelectedItem();
			System.out.println(this.Hazardous);
		});

		this.contexts = new StringProperty[contexts.length];

		for(int i=0;i<contexts.length;i++) {
			this.contexts[i] = new SimpleStringProperty(contexts[i]);
		}

		this.ControlAction = controlAction;
		this.Cases = cases;
		this.No = no;
		this.Contexts = contexts;
		this.Hazardous = hazardous.getValue();
	}

	public void setCTMInit(){
		this.controlAction = new SimpleStringProperty(this.ControlAction);
		this.cases = new SimpleStringProperty(this.Cases);
		this.no = new SimpleIntegerProperty(this.No);
		this.contexts = new StringProperty[Contexts.length];
		for(int i=0;i<Contexts.length;i++) {
			this.contexts[i] = new SimpleStringProperty(Contexts[i]);
		}
	}

	public String getContext(int i) {
		return contexts[i].get();
	}

	public StringProperty getContextProperty(int i) {
		return contexts[i];
	}
	public void setContext(int i, String val) {
		this.contexts[i].set(val);
	}

	public String getControlAction() {
		return controlAction.get();
	}

	public StringProperty getControlActionProperty() {
		return controlAction;
	}
	public void setControlAction(String val) {
		this.controlAction.set(val);
	}

	public String getCases() {
		return cases.get();
	}

	public StringProperty getCasesProperty() {
		return cases;
	}
	public void setCases(String val) {
		this.cases.set(val);
	}

	public int getNo() {
		return no.get();
	}

	public IntegerProperty getNoProperty() {
		return no;
	}

	public ComboBox getHazardousList() {
		return hazardous;
	}

	public String getHazardous(){
		return Hazardous;
	}

	public String getHazardousValue() {
		return Hazardous;
	}

	public void setHazardous(ComboBox val) {
		this.hazardous = val;
		this.hazardous.setValue(Hazardous);
		hazardous.setOnAction(event ->{
			this.Hazardous = hazardous.getSelectionModel().getSelectedItem();
			System.out.println(this.Hazardous);
		});
	}



}