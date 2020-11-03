package kutokit.model.lhc;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.ProjectXML;

public class lossListAdapter extends XmlAdapter<ProjectXML, ObservableList<LHC>>{

	@Override
	public ObservableList<LHC> unmarshal(ProjectXML v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LHC> lossTableList = FXCollections.observableArrayList(v.getLossList());
		return lossTableList;
	}

	@Override
	public ProjectXML marshal(ObservableList<LHC> v) throws Exception {
		// TODO Auto-generated method stub
		ProjectXML lhcWrapper = new ProjectXML();
		v.stream().forEach((item) -> {
			lhcWrapper.getLossList().add(item);
		});
		return lhcWrapper;
	}
	
	
	
}