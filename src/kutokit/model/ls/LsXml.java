package kutokit.model.ls;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LS_controller")
public class LsXml {
	LSDataStore lsDB = new LSDataStore();
	List<LS> lossScenarioList = new ArrayList<LS>();
	
	@XmlElement(name = "loss_scenario")
	public List<LS> getLossScenarioList(){
		return this.lsDB.getLossScenarioList();
	}
	
	public void setLossScenarioList(List<LS> lossScenarioList) {
		this.lsDB.getLossScenarioList().setAll(lossScenarioList);
	}
}