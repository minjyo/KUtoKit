package kutokit.model.ls;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.lhc.lossListAdapter;
import kutokit.model.utm.UCA;

public class LSDataStore {
	private ObservableList<LS> lsUcaList = FXCollections.observableArrayList();
	private ObservableList<LS> lossFactorList = FXCollections.observableArrayList();
	private ObservableList<LS> lossScenarioList = FXCollections.observableArrayList();
	
	@XmlElement(name = "LS-UCA")
	@XmlJavaTypeAdapter(lsUcaListAdapter.class)
	public ObservableList<LS> getLsUcaList(){
		return this.lsUcaList; 
	}
	
	public void setLsUcaList(ObservableList<LS> lsUcaList) {
		this.lsUcaList = lsUcaList;
	}
	
	public void addLsUca(LS lsUca) {
		this.lsUcaList.add(lsUca);
	}
	
	@XmlElement(name = "LS-loss-factor")
	@XmlJavaTypeAdapter(lossFactorListAdapter.class)
	public ObservableList<LS> getLossFactorList(){
		return this.lossFactorList; 
	}
	
	public void setLossFactorList(ObservableList<LS> lossFactorList) {
		this.lossFactorList = lossFactorList;
	}
	
	public void addLossFactor(LS lossFactor) {
		this.lsUcaList.add(lossFactor);
	}
	
	@XmlElement(name = "LS-loss-scenario")
	@XmlJavaTypeAdapter(lsListAdapter.class)
	public ObservableList<LS> getLossScenarioList(){
		return this.lossScenarioList;
	}
	
	public void setLossScenarioList(ObservableList<LS> lossScenarioList) {
		this.lossScenarioList = lossScenarioList;
	}
	
	public void addLossScenario(LS ls) {
		this.lossScenarioList.add(ls);
	}
}