
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
	private StringProperty controllerName, controlActionName;
	private ComboBox cases, hazardous;
	private IntegerProperty no;
	private ObservableList<StringProperty> contexts;

	private String ControllerName;
	private String ControlAction;
	private String Cases;
	private int No;
	private String Hazardous;
	private String[] Contexts;

	/*
	 * default constructor
	 */
	public CTM() {
	}
	
	/*
	 * initialize constructor
	 */
	public CTM(String controllerName, String controlActionName, int no) {
		this.controllerName = new SimpleStringProperty(controllerName);
		this.controlActionName = new SimpleStringProperty(controlActionName);
		this.cases = new ComboBox(); 
		this.no = new SimpleIntegerProperty(no);
		this.hazardous = hazardous;
		cases.setValue("not providing\ncauses hazard");
		hazardous.setValue("X");
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
	
	public CTM(String string, ArrayList<String> controlActionNames2, ComboBox<String> comboBox1, int i,
			ObservableList<StringProperty> contexts2, ComboBox<String> comboBox2) {
		// TODO Auto-generated constructor stub
		this.controllerName = new SimpleStringProperty(string);
		this.controlActionName = new SimpleStringProperty(controlActionNames2.get(i));
		this.cases = comboBox1;
		this.no = new SimpleIntegerProperty(i);
		this.contexts = contexts2;
	}

	public StringProperty getControllerNameProperty() {
		return this.controllerName;
	}
	
	public String getControllerName() {
		return this.controllerName.get();
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
	
	public StringProperty getCANameProperty() {
		return this.controlActionName;
	}
	
	public String getCAName() {
		return this.controlActionName.get();
	}
	
	public void setCAName(String caName) {
		this.controlActionName.set(caName);
	}
	
	public ComboBox getCases() {
		return this.cases;
	}
	
//	public void setCases(ComboBox cases) {
//		this.cases = cases;
//	}
	
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
	
	public StringProperty getContextProperty(int i) {
		return contexts.get(i);
	}

	public void setHazardous(ComboBox<String>  val) {
		this.hazardous = val;
		Hazardous = hazardous.getValue();
	}
	
	public ComboBox getHazardousList() {
		return hazardous;
	}
	
	public CTM get(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}