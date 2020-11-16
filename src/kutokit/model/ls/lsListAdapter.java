package kutokit.model.ls;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.ProjectXML;

public class lsListAdapter extends XmlAdapter<ProjectXML, ObservableList<LS>> {

	@Override
	public ObservableList<LS> unmarshal(ProjectXML v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LS> lossScenarioList = FXCollections.observableArrayList(v.getLsList());
		return lossScenarioList;
	}

	@Override
	public ProjectXML marshal(ObservableList<LS> v) throws Exception {
		// TODO Auto-generated method stub
		ProjectXML lsXml = new ProjectXML();
		v.stream().forEach((ls) -> {
			lsXml.getLsList().add(ls);
		});
		return lsXml;
	}
	
	
}