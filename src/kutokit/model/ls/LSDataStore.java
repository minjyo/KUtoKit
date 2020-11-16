package kutokit.model.ls;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.lhc.lossListAdapter;
import kutokit.model.utm.UCA;

public class LSDataStore {
	private ObservableList<LS> lsList = FXCollections.observableArrayList();
	
	@XmlElement(name = "LS-loss-scenario")
	@XmlJavaTypeAdapter(lsListAdapter.class)
	public ObservableList<LS> getLsList(){
		return this.lsList;
	}
	
	public void setLossScenarioList(ObservableList<LS> lsList) {
		this.lsList = lsList;
	}
	
	public void addLossScenario(LS ls) {
		this.lsList.add(ls);
	}
}