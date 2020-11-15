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
	private StringProperty controllerName;
	private StringProperty controlAction;
	private ComboBox<String> cases;
	private IntegerProperty no;
	private ComboBox<String> hazardous;
	private StringProperty[] contexts;

	private String ControllerName;
	private String ControlAction;
	private String Cases;
	private int No;
	private String Hazardous;
	private String[] Contexts;

	public CTM(String controllerName, String controlAction, ComboBox<String> cases, int no, String[] contexts, ComboBox<String> hazardous) {
		this.controllerName = new SimpleStringProperty(controllerName);
		this.controlAction = new SimpleStringProperty(controlAction);
		this.cases = cases;
		this.no = new SimpleIntegerProperty(no);
		this.hazardous = hazardous;
		this.contexts = new StringProperty[contexts.length];
		
		cases.setOnAction(event -> {
			System.out.println("cases.getValue():"+cases.getValue());
			this.Cases = cases.getValue();
		});

		hazardous.setOnAction(event -> {
			System.out.println("hazardous.getValue():"+hazardous.getValue());
			this.Hazardous = hazardous.getValue();
		});


		for(int i=0;i<contexts.length;i++) {
			this.contexts[i] = new SimpleStringProperty(contexts[i]);
		}

		this.ControllerName = controllerName;
		this.ControlAction = controlAction;
		this.Cases = cases.getValue();
		this.No = no;
		this.Contexts = contexts;
		this.Hazardous = hazardous.getValue();
	}
	
	public void setCTMInit(){
		this.controlAction = new SimpleStringProperty(this.ControlAction);
		this.no = new SimpleIntegerProperty(this.No);
		this.contexts = new StringProperty[Contexts.length];
		for(int i=0;i<Contexts.length;i++) {
			this.contexts[i] = new SimpleStringProperty(Contexts[i]);
		}
	}
	
	public String getControllerName() {
		return controllerName.get();
	}
	
	public StringProperty getControllerNameProperty() {
		return controllerName;
	}
	public void setControllerName(String val) {
		this.controllerName.set(val);
	}

	public String[] getContexts() {
		return Contexts;
	}
	
	public String getContext(int i) {
		return contexts[i].get();
	}
	
	public StringProperty getContextProperty(int i) {
		//System.out.println("property["+i+"]:"+test[i]);
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
	
	
	public int getNo() {
		return no.get();
	}
	
	public IntegerProperty getNoProperty() {
		return no;
	}

	public ComboBox<String> getCases() {
		return cases;
	}
	public String getCasesValue() {
		return Cases;
	}

	public void setCases(ComboBox<String> val) {
		this.cases = val;
		Cases = cases.getValue();
	}
	
	public void setCasesValue(String val) {
		this.cases.setValue(val);
		Cases = cases.getValue();
	}

	public ComboBox<String> getHazardous() {
		return hazardous;
	}
	
	public String getHazardousValue() {
		return Hazardous;
	}

	public void setHazardous(ComboBox<String>  val) {
		this.hazardous = val;
		Hazardous = hazardous.getValue();
	}
	
	public void setHazardousValue(String val) {
		this.hazardous.setValue(val);
		Hazardous = hazardous.getValue();
	}
	public CTM get(int i) {
		// TODO Auto-generated method stub
		return null;
	}


}