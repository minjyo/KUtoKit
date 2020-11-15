
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

<<<<<<< HEAD
	private int No;
=======
	private String ControllerName;
	private String ControlAction;
	private String Cases;
	private int No;
	private String Hazardous;
>>>>>>> master
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
<<<<<<< HEAD
		this.hazardous = new ComboBox();
		this.contexts = FXCollections.observableArrayList();
=======
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
>>>>>>> master
	}

	public StringProperty getControllerNameProperty() {
		return this.controllerName;
	}

	public String getControllerName() {
		return this.controllerName.get();
	}

<<<<<<< HEAD
	public void setControllerName(String controllerName) {
		this.controllerName.set(controllerName);
=======
	public String[] getContexts() {
		return Contexts;
	}
	
	public String getContext(int i) {
		return contexts[i].get();
>>>>>>> master
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

	public ComboBox getCasesList() {
		return this.cases;
	}

	public void setCases(ComboBox cases) {
		this.cases = cases;
	}

	public int getNo() {
		return no.get();
	}

	public IntegerProperty getNoProperty() {
		return no;
	}
	
	
	

<<<<<<< HEAD
	public void setNo(int num) {
		this.no.set(num);
=======
	
	
	

	public ComboBox<String> getCases() {
		return cases;
>>>>>>> master
	}

	public String getContext(int i) {
		return contexts.get(i).get();
	}

<<<<<<< HEAD
	public StringProperty getContextProperty(int i) {
		return contexts.get(i);
=======
	public void setCases(ComboBox<String> val) {
		this.cases = val;
		Cases = cases.getValue();
>>>>>>> master
	}

	public void setContext(int i, StringProperty val) {
		this.contexts.set(i, val);
	}

<<<<<<< HEAD
	public ComboBox getHazardousList() {
=======
	public ComboBox<String> getHazardous() {
>>>>>>> master
		return hazardous;
	}

<<<<<<< HEAD
	public void setHazardous(ComboBox hazardous) {
		this.hazardous = hazardous;
	}
}
=======
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
>>>>>>> master
