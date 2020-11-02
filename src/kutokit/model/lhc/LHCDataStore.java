package kutokit.model.lhc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LHCDataStore {

	ObservableList<LHC> lossTableList = FXCollections.observableArrayList();
	ObservableList<LHC> hazardTableList = FXCollections.observableArrayList();
	ObservableList<LHC> constraintTableList = FXCollections.observableArrayList();

	public ObservableList<LHC> getLossTableList() {
		return this.lossTableList;
	}
	
	public ObservableList<LHC> getHazardTableList() {
		return this.hazardTableList;
	}
	
	public ObservableList<LHC> getConstraintTableList() {
		return this.constraintTableList;
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
