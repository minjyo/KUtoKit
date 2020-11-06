package kutokit.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import kutokit.model.cse.ControlAction;
import kutokit.model.cse.Controller;
import kutokit.model.cse.Feedback;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LhcDataStore;
import kutokit.model.pmm.ProcessModel;
import kutokit.model.utm.UCA;
import kutokit.view.components.*;

@XmlRootElement(name = "kutokit")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProjectXML {
	
	// --------------------------- LHC --------------------------
	LhcDataStore lhcDB = new LhcDataStore();
	List<LHC> lossList = new ArrayList<LHC>();
	List<LHC> hazardList = new ArrayList<LHC>();
	List<LHC> constraintList = new ArrayList<LHC>();
	// --------------------------- LHC --------------------------
	
	
	// --------------------------- CSE --------------------------
	private ArrayList<Controller> controllers;
	private ArrayList<ControlAction> controlActions = new ArrayList<ControlAction>();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	private int curId;
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

	
	
	// --------------------------- LHC --------------------------
	@XmlElement(name = "Loss")
	public List<LHC> getLossList(){
		return this.lhcDB.getLossTableList();
	}
	
	public void setLossList(List<LHC> lossList) {
		this.lhcDB.getLossTableList().setAll(lossList);
	}
	
	@XmlElement(name = "Hazard")
	public List<LHC> getHazardList(){
		return this.lhcDB.getHazardTableList();
	}
	
	public void setHazardList(List<LHC> hazardList) {
		this.lhcDB.getHazardTableList().setAll(hazardList);
	}
	
	@XmlElement(name = "Constraint")
	public List<LHC> getConstraintList(){
		return this.lhcDB.getConstraintTableList();
	}
	
	public void setConstraintList(List<LHC> constraintList) {
		this.lhcDB.getConstraintTableList().setAll(constraintList);
	}
	// --------------------------- LHC --------------------------
	
	
	
	// --------------------------- CSE --------------------------
	@XmlElement(name = "CSEcontroller")
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
	
	@XmlElement(name = "curId")
	public int getCurId() {
		return curId;
	}
	
	public void setCurId(int id) {
		this.curId = id;
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
	@XmlElement(name = "PMMController")
	public String getControllerName() {
		return controller;
	}
	public void setControllerName(String controllerName) {
		this.controller = controllerName;
	}
	
	@XmlElement(name = "PMMControlAction")
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
