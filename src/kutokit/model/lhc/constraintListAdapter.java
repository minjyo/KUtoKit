package kutokit.model.lhc;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.model.ProjectXML;

public class constraintListAdapter extends XmlAdapter<ProjectXML, ObservableList<LHC>>{

	@Override
	public ObservableList<LHC> unmarshal(ProjectXML v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LHC> constraintTableList = FXCollections.observableArrayList(v.getConstraintList());
		return constraintTableList;
	}

	@Override
	public ProjectXML marshal(ObservableList<LHC> v) throws Exception {
		// TODO Auto-generated method stub
		ProjectXML lhcWrapper = new ProjectXML();
		v.stream().forEach((lhc) -> {
			lhcWrapper.getConstraintList().add(lhc);
		});
		return lhcWrapper;
	}
	
	
	
}