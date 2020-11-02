package kutokit.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCA;
import kutokit.view.components.*;

@XmlRootElement(name = "kutokit")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProjectXML {

	// --------------------------- CSE --------------------------
	private ArrayList<Controller> controllers;
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	// --------------------------- CSE --------------------------
	
	// --------------------------- UTM --------------------------
	ObservableList<UCA> UCAList = FXCollections.observableArrayList();
	// --------------------------- UTM --------------------------
	
	// --------------------------- PMM --------------------------
	private String controller;
	private String controlAction;
	private String outputVariable;
	private ObservableList<String> valueList = FXCollections.observableArrayList();
	// --------------------------- PMM --------------------------

	// --------------------------- CSE --------------------------
	@XmlElement(name = "CSEontroller")
	public ArrayList<Controller> getControllers() {
		return controllers;
	}

	public void setControllers(ArrayList<Controller> controllers) {
		this.controllers = controllers;
	}
	
	@XmlElement(name = "CSEcontrolActions")
	public ArrayList<ControlAction> getControlActions() {
		return controlActions;
	}

	public void setControlActions(ArrayList<ControlAction> controlActions) {
		this.controlActions = controlActions;
	}
	
	@XmlElement(name = "CSEfeedbacks")
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	// --------------------------- CSE --------------------------
	
	
	// --------------------------- UTM --------------------------
	@XmlElement(name = "UCA")
	public ObservableList<UCA> getUCAList() {
		return this.UCAList;
	}

	public void setUCAList(ObservableList<UCA> UCAList) {
		this.UCAList = UCAList;
	}
	// --------------------------- UTM --------------------------
	
	// --------------------------- PMM --------------------------
	@XmlElement(name = "Controller")
	public String getControllerName() {
		return controller;
	}
	public void setControllerName(String controllerName) {
		this.controller = controllerName;
	}
	
	@XmlElement(name = "ControlAction")
	public String getControlActionName() {
		return controlAction;
	}
	public void setControlActionName(String controlActionName) {
		this.controlAction = controlActionName;
	}
	
	@XmlElement(name = "OutputVariable")
	public String getOutputVariableName() {
		return outputVariable;
	}
	public void setOutputVariableName(String OutputVariableName) {
		outputVariable = OutputVariableName;
	}
	
	@XmlElement(name = "Valuelist")
	public ObservableList<String> getValueList() {
		return valueList;
	}
	public void setValueList(ObservableList<String> valueListName) {
		valueList = valueListName;
	}
	
	// --------------------------- PMM --------------------------
}
