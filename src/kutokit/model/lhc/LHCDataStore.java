package kutokit.model.lhc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LHCDataStore {

	private ObservableList<LHC> lossTableList = FXCollections.observableArrayList();
	private ObservableList<LHC> hazardTableList = FXCollections.observableArrayList();
	private ObservableList<LHC> constraintTableList = FXCollections.observableArrayList();

	@XmlElement(name = "Loss")
	@XmlJavaTypeAdapter(lossListAdapter.class)
	public ObservableList<LHC> getLossTableList() {
		return this.lossTableList;
	}
	
	@XmlElement(name = "Hazard")
	@XmlJavaTypeAdapter(hazardListAdapter.class)
	public ObservableList<LHC> getHazardTableList() {
		return this.hazardTableList;
	}
	
	@XmlElement(name = "Constraint")
	@XmlJavaTypeAdapter(constraintListAdapter.class)
	public ObservableList<LHC> getConstraintTableList() {
		return this.constraintTableList;
	}
	
	public void setLossTableList(ObservableList<LHC> lossTableList) {
		this.lossTableList = lossTableList;
	}
	
	public void setHazardTableList(ObservableList<LHC> hazardTableList) {
		this.hazardTableList = hazardTableList;
	}
	
	public void setConstraintTableList(ObservableList<LHC> constraintTableList) {
		this.constraintTableList = constraintTableList;
	}
	
	public void addLoss(LHC lhc) {
		this.lossTableList.add(lhc);
	}
	
	public void addHazard(LHC lhc) {
		this.hazardTableList.add(lhc);
	}
	
	public void addConstraint(LHC lhc) {
		this.constraintTableList.add(lhc);
	}
}
