package kutokit.model.ls;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.lhc.lossListAdapter;

public class LSDataStore {
	
	private ObservableList<LS> lossScenarioList = FXCollections.observableArrayList();
	
	@XmlElement(name = "Loss_Scenario")
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