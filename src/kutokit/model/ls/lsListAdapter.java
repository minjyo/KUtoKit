package kutokit.model.ls;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class lsListAdapter extends XmlAdapter<LsXml, ObservableList<LS>> {

	@Override
	public ObservableList<LS> unmarshal(LsXml v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LS> lossScenarioList = FXCollections.observableArrayList(v.getLossScenarioList());
		return lossScenarioList;
	}

	@Override
	public LsXml marshal(ObservableList<LS> v) throws Exception {
		// TODO Auto-generated method stub
		LsXml lsXml = new LsXml();
		v.stream().forEach((ls) -> {
			lsXml.getLossScenarioList().add(ls);
		});
		return lsXml;
	}
	
	
}