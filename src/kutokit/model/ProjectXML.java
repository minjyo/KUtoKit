package kutokit.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.StringProperty;
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
import kutokit.model.pmm.ProcessModel;
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
	private ObservableList<ProcessModel> processModel = FXCollections.observableArrayList();
	private ArrayList<ArrayList<String>> inputVariables = new ArrayList<ArrayList<String>>();
	private ObservableList<String> outputVariables = FXCollections.observableArrayList();
	// --------------------------- PMM --------------------------


	// --------------------------- CTM --------------------------
	ObservableList<CTM> CTM = FXCollections.observableArrayList();
	ObservableList<CTMDataStore> CTMList = FXCollections.observableArrayList();
	// --------------------------- CTM --------------------------


	// --------------------------- LS ---------------------------
	LSDataStore lsDB = new LSDataStore();
	List<LS> lsList = new ArrayList<LS>();
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




	// --------------------------- UTM --------------------------
	@XmlElement(name = "UCA-List")
	public ObservableList<UCADataStore> getUCADataStoreList() {
		return this.UCAList;
	}

	@XmlElement(name = "UCA")
	public ObservableList<UCA> getUCA(){
		return UCA;
	}

	public void setUCAList(ObservableList<UCADataStore> UCAList) {
		this.UCAList = UCAList;
	}

	public void setUCA(ObservableList<UCA> UCA) {
		this.UCA = UCA;
	}
	// --------------------------- UTM --------------------------



	// --------------------------- PMM --------------------------
	@XmlElement(name = "PMM-process-model")
	public ObservableList<ProcessModel> getProcessModel(){
		return processModel;
	}

	public void setProcessModel(ObservableList<ProcessModel> pm) {
		this.processModel = pm;
	}

	@XmlElement(name = "PMM-total-selected-output")
	public ObservableList<String> getOutputList(){
		return outputVariables;
	}

	public void setOutputList(ObservableList<String> outputList) {
		this.outputVariables = outputList;
	}

	@XmlElementWrapper(name="PMM-input-list")
	@XmlElement(name = "Input-variables")
	public ArrayList<ArrayList<String>> getInputList(){
		return inputVariables;
	}

	public void setIntputList(ArrayList<ArrayList<String>> inputList) {
		this.inputVariables = inputList;
	}

	// --------------------------- CTM --------------------------
	@XmlElement(name = "CTM-List")
	public ObservableList<CTMDataStore> getCtmDataStoreList() {
		return this.CTMList;
	}

	public void setCTMList(ObservableList<CTMDataStore> CTMList) {
		this.CTMList = CTMList;
	}


	public ArrayList<String> getCTMControllerName(){
		ArrayList<String> controllerNames = new ArrayList<String>();
		for(int i=0;i<CTMList.size();i++){
			for(int j=0;j<CTMList.get(i).getCTMTableList().size();j++){
				controllerNames.add(CTMList.get(i).getCTMTableList().get(j).getControllerName());
			}
		}
		return controllerNames;
	}

	public ArrayList<String> getCTMCA(){
		ArrayList<String> ca = new ArrayList<String>();
		for(int i=0;i<CTMList.size();i++){
			for(int j=0;j<CTMList.get(i).getCTMTableList().size();j++){
				ca.add(CTMList.get(i).getCTMTableList().get(j).getControlAction());
			}
		}
		return ca;
	}

	public ArrayList<String> getCTMCases(){
		ArrayList<String> cases = new ArrayList<String>();
		for(int i=0;i<CTMList.size();i++){
			for(int j=0;j<CTMList.get(i).getCTMTableList().size();j++){
				cases.add(CTMList.get(i).getCTMTableList().get(j).getCasesValue());
			}
		}
		return cases;
	}

	public ArrayList<String[]> getCTMContext(){
		ArrayList<String[]> contextsArray = new ArrayList<String[]>();
		for(int i=0;i<CTMList.size();i++){
			for(int j=0;j<CTMList.get(i).getCTMTableList().size();j++){
				String[] contexts =  new String[CTMList.get(i).getCTMTableList().get(0).getContexts().length];
				for(int k=0;k<contexts.length;k++) {
					contexts[k] = CTMList.get(i).getCTMTableList().get(j).getContext(k);
				}
				contextsArray.add(contexts);
			}
		}
		return contextsArray;
	}

	public ArrayList<String> getCTMHazardous(){
		ArrayList<String> hazardous = new ArrayList<String>();
		for(int i=0;i<CTMList.size();i++){
			for(int j=0;j<CTMList.get(i).getCTMTableList().size();j++){
				hazardous.add(CTMList.get(i).getCTMTableList().get(j).getHazardous().toString());
			}
		}
		return hazardous;
	}

	public void setCTM(ObservableList<CTM> CTM) {
		this.CTM = CTM;
	}
	// --------------------------- CTM --------------------------


	// --------------------------- LS ---------------------------
	@XmlElement(name = "LS-loss-scenario")
	public List<LS> getLsList(){
		return this.lsDB.getLsList();
	}

	public void setLsList(List<LS> lsList) {
		this.lsDB.getLsList().setAll(lsList);
	}
	// --------------------------- LS ---------------------------
}