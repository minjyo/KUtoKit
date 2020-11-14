package kutokit.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import kutokit.model.cse.ControlAction;
import kutokit.model.cse.Controller;
import kutokit.model.cse.Feedback;
import kutokit.model.cse.Text;
import kutokit.model.ctm.CTM;
import kutokit.model.ctm.CTMDataStore;
import kutokit.model.lhc.LHC;
import kutokit.model.lhc.LhcDataStore;
import kutokit.model.ls.LS;
import kutokit.model.ls.LSDataStore;
import kutokit.model.utm.UCA;
import kutokit.model.utm.UCADataStore;

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
	private ArrayList<Text> texts = new ArrayList<Text>();
	private int curId;
	// --------------------------- CSE --------------------------


	// --------------------------- UTM --------------------------
	ObservableList<UCA> UCA = FXCollections.observableArrayList();
	ObservableList<UCADataStore> UCAList = FXCollections.observableArrayList();
	// --------------------------- UTM --------------------------


	// --------------------------- PMM --------------------------
	private ArrayList<String> controller = new ArrayList<String>();

	private ArrayList<ArrayList<String>> selectedCAs = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> outputVariables = new ArrayList<ArrayList<String>>();

	private ArrayList<ArrayList<String>> allCAs = new ArrayList<ArrayList<String>>();

	private ObservableList<String> allOutput =  FXCollections.observableArrayList();
	private ObservableList<String> valueList = FXCollections.observableArrayList();
	// --------------------------- PMM --------------------------


	// --------------------------- CTM --------------------------
	ObservableList<CTM> CTM = FXCollections.observableArrayList();
	ObservableList<CTMDataStore> CTMList = FXCollections.observableArrayList();
	// --------------------------- CTM --------------------------


	// --------------------------- LS ---------------------------
	LSDataStore lsDB = new LSDataStore();
	List<LS> lsUcaList = new ArrayList<LS>();
	List<LS> lossFactorList = new ArrayList<LS>();
	List<LS> lossScenarioList = new ArrayList<LS>();
	// --------------------------- LS ---------------------------


	// --------------------------- LHC --------------------------
	@XmlElement(name = "LHC-loss")
	public List<LHC> getLossList(){
		return this.lhcDB.getLossTableList();
	}

	public void setLossList(List<LHC> lossList) {
		this.lhcDB.getLossTableList().setAll(lossList);
	}

	@XmlElement(name = "LHC-hazard")
	public List<LHC> getHazardList(){
		return this.lhcDB.getHazardTableList();
	}

	public void setHazardList(List<LHC> hazardList) {
		this.lhcDB.getHazardTableList().setAll(hazardList);
	}

	@XmlElement(name = "LHC-constraint")
	public List<LHC> getConstraintList(){
		return this.lhcDB.getConstraintTableList();
	}

	public void setConstraintList(List<LHC> constraintList) {
		this.lhcDB.getConstraintTableList().setAll(constraintList);
	}
	// --------------------------- LHC --------------------------



	// --------------------------- CSE --------------------------
	@XmlElement(name = "CSE-controller")
	public ArrayList<Controller> getControllers() {
		return controllers;
	}

	public void setControllers(ArrayList<Controller> controllers) {
		this.controllers = controllers;
	}

	@XmlElement(name = "CSE-control-Actions")
	public ArrayList<ControlAction> getControlActions() {
		return controlActions;
	}

	public void setControlActions(ArrayList<ControlAction> controlActions) {
		this.controlActions = controlActions;
	}

	@XmlElement(name = "CSE-feedbacks")
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@XmlElement(name = "CSE-texts")
	public ArrayList<Text> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<Text> texts) {
		this.texts = texts;
	}

	@XmlElement(name = "cur-Id")
	public int getCurId() {
		return curId;
	}

	public void setCurId(int id) {
		this.curId = id;
	}
	// --------------------------- CSE --------------------------

	// --------------------------- PMM --------------------------
	@XmlElement(name = "PMM-controller")
	public ArrayList<String> getControllerName() {
		return controller;
	}
	public void setControllerName(ArrayList<String> controllerName) {
		this.controller = controllerName;
	}

	@XmlElement(name = "PMMControlAction")
	public ArrayList<ArrayList<String>> getControlActionNames() {
		return selectedCAs;
	}
	public void setControlActionNames(ArrayList<ArrayList<String>> controlActionName) {
		this.selectedCAs = controlActionName;
	}

	@XmlElementWrapper(name="PMM-output-list")
	@XmlElement(name = "Output")
	public ArrayList<ArrayList<String>> getOutputVariableName() {
		return outputVariables;
	}
	public void setOutputVariableName(ArrayList<ArrayList<String>> outputVariables) {
		this.outputVariables = outputVariables;
	}

	@XmlElementWrapper(name="PMM-value-list")
	@XmlElement(name = "Value")
	public ObservableList<String> getValueList() {
		return valueList;
	}
	public void setValueList(ObservableList<String> valueListName) {
		valueList = valueListName;
	}

	@XmlElementWrapper(name="PMM-all-CA")
	@XmlElement(name = "Allca")
	public ArrayList<ArrayList<String>> getAllCA() {
		return allCAs;
	}

	public void setAllCA(ArrayList<ArrayList<String>> controlAction) {
		this.allCAs = controlAction;
	}

	@XmlElementWrapper(name="PMM-all-output")
	@XmlElement(name = "Alloutput")
	public ObservableList<String> getAllOutput() {
		return allOutput;
	}

	public void setAllOutput(ObservableList<String> allOutput) {
		this.allOutput = allOutput;
	}
	// --------------------------- PMM --------------------------


	// --------------------------- UTM --------------------------
	@XmlElement(name = "UCA-List")
	public ObservableList<UCADataStore> getUCADataStoreList() {
		return this.UCAList;
	}

	@XmlElement(name = "UCA")
	public ObservableList<UCA> getUCA(){
		for(UCADataStore u : UCAList){
			UCA.addAll(u.getUCATableList());
		}
		return UCA;
	}

	public void setUCAList(ObservableList<UCADataStore> UCAList) {
		this.UCAList = UCAList;
	}

	public void setUCA(ObservableList<UCA> UCA) {
		this.UCA = UCA;
	}
	// --------------------------- UTM --------------------------


	// --------------------------- CTM --------------------------
	@XmlElement(name = "CTM-List")
	public ObservableList<CTMDataStore> getCtmDataStoreList() {
		return this.CTMList;
	}

	@XmlElement(name = "CTM")
	public ObservableList<CTM> getCTM(){
		for(CTMDataStore c : CTMList){
			CTM.addAll(c.getCTMTableList());
		}
		return CTM;
	}

	public void setCTMList(ObservableList<CTMDataStore> CTMList) {
		this.CTMList = CTMList;
	}

	public void setCTM(ObservableList<CTM> CTM) {
		this.CTM = CTM;
	}
	// --------------------------- CTM --------------------------


	// --------------------------- LS ---------------------------
	@XmlElement(name = "LS-UCA")
	public List<LS> getLsUcaList(){
		return this.lsDB.getLsUcaList();
	}

	public void setLsUcaList(List<LS> lsUcaList) {
		this.lsDB.getLsUcaList().setAll(lsUcaList);
	}

	@XmlElement(name = "LS-loss-factor")
	public List<LS> getLossFactorList(){
		return this.lsDB.getLossFactorList();
	}

	public void setLossFactorList(List<LS> lossFactorList) {
		this.lsDB.getLossFactorList().setAll(lossFactorList);
	}

	@XmlElement(name = "LS-loss-scenario")
	public List<LS> getLossScenarioList(){
		return this.lsDB.getLossScenarioList();
	}

	public void setLossScenarioList(List<LS> lossScenarioList) {
		this.lsDB.getLossScenarioList().setAll(lossScenarioList);
	}
	// --------------------------- LS ---------------------------
}
