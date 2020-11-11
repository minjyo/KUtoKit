package kutokit.model.ls;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.ProjectXML;

public class lossFactorListAdapter extends XmlAdapter<ProjectXML, ObservableList<LS>> {

	@Override
	public ObservableList<LS> unmarshal(ProjectXML v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LS> lossFactorList = FXCollections.observableArrayList(v.getLossFactorList());
		return lossFactorList;
	}

	@Override
	public ProjectXML marshal(ObservableList<LS> v) throws Exception {
		// TODO Auto-generated method stub
		ProjectXML lsXml = new ProjectXML();
		v.stream().forEach((ls) -> {
			lsXml.getLossFactorList().add(ls);
		});
		return lsXml;
	}
	
	
}