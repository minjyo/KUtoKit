package kutokit.model;

import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.view.components.*;

@XmlRootElement(name = "LHC")
//@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class LHCXML {

	LHCDataStore lhcDB = new LHCDataStore();
	ObservableList<LHC> lossTableList = FXCollections.observableArrayList();
	ObservableList<LHC> hazardTableList = FXCollections.observableArrayList();
	ObservableList<LHC> constraintTableList = FXCollections.observableArrayList();

	
	@XmlElement(name = "Loss")
	public List<LHC> getLossTableList() {
		return this.lhcDB.getLossTableList();
	}

	public void setLoss(List<LHC> lossTableList) {
		this.lhcDB.getLossTableList().setAll(lossTableList);
	}
	
	@XmlElement(name = "Hazard")
	public List<LHC> getHazardTableList() {
		return this.lhcDB.getHazardTableList();
	}

	public void setHazard(List<LHC> hazardTableList) {
		this.lhcDB.getHazardTableList().setAll(hazardTableList);
	}
	
	@XmlElement(name = "Constraint")
	public List<LHC> getConstraintTableList() {
		return this.lhcDB.getConstraintTableList();
	}

	public void setConstraint(ObservableList<LHC> constraintTableList) {
		this.lhcDB.getConstraintTableList().setAll(constraintTableList);
	}
}
