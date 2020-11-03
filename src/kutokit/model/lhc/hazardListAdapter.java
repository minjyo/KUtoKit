package kutokit.model.lhc;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class hazardListAdapter extends XmlAdapter<LhcWrapper, ObservableList<LHC>>{

	@Override
	public ObservableList<LHC> unmarshal(LhcWrapper v) throws Exception {
		// TODO Auto-generated method stub
		ObservableList<LHC> hazardTableList = FXCollections.observableArrayList(v.getHazardList());
		return hazardTableList;
	}

	@Override
	public LhcWrapper marshal(ObservableList<LHC> v) throws Exception {
		// TODO Auto-generated method stub
		LhcWrapper lhcWrapper = new LhcWrapper();
		v.stream().forEach((item) -> {
			lhcWrapper.getHazardList().add(item);
		});
		return lhcWrapper;
	}
	
	
	
}