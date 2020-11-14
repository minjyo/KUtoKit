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

public class CTM2 {
	private StringProperty controllerName, controlActionName;
	private ComboBox cases, hazardous;
	private IntegerProperty no;
	private ObservableList<StringProperty> contexts;

	private int No;
	private String[] Contexts;

	/*
	 * default constructor
	 */
	public CTM2() {
	}

	/*
	 * initialize constructor
	 */
	public CTM2(String controllerName, String controlActionName, int no) {
		this.controllerName = new SimpleStringProperty(controllerName);
		this.controlActionName = new SimpleStringProperty(controlActionName);
		this.cases = new ComboBox();
		this.no = new SimpleIntegerProperty(no);
		this.hazardous = new ComboBox();
		this.contexts = FXCollections.observableArrayList();
	}

	public StringProperty getControllerNameProperty() {
		return this.controllerName;
	}

	public String getControllerName() {
		return this.controllerName.get();
	}

	public void setControllerName(String controllerName) {
		this.controllerName.set(controllerName);
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

	public void setNo(int num) {
		this.no.set(num);
	}

	public String getContext(int i) {
		return contexts.get(i).get();
	}

	public StringProperty getContextProperty(int i) {
		return contexts.get(i);
	}

	public void setContext(int i, StringProperty val) {
		this.contexts.set(i, val);
	}

	public ComboBox getHazardousList() {
		return hazardous;
	}

	public void setHazardous(ComboBox hazardous) {
		this.hazardous = hazardous;
	}
}
