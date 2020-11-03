package kutokit.model.lhc;

import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/*
 * wrapping each observableList into list
 * used for marshalling/unmarshalling into xml file
 */
@XmlRootElement(name = "LHC")
//@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class LhcWrapper {
	LhcDataStore lhcDB = new LhcDataStore();
	List<LHC> lossList = new ArrayList<LHC>();
	List<LHC> hazardList = new ArrayList<LHC>();
	List<LHC> constraintList = new ArrayList<LHC>();

	public LhcWrapper() {
	}
	
	@XmlElement(name = "Loss")
	public List<LHC> getLossList(){
		return this.lhcDB.getLossTableList();
	}
	
	public void setLossList(List<LHC> lossList) {
		this.lhcDB.getLossTableList().setAll(lossList);
	}
	
	@XmlElement(name = "Hazard")
	public List<LHC> getHazardList(){
		return this.lhcDB.getHazardTableList();
	}
	
	public void setHazardList(List<LHC> hazardList) {
		this.lhcDB.getHazardTableList().setAll(hazardList);
	}
	
	@XmlElement(name = "Constraint")
	public List<LHC> getConstraintList(){
		return this.lhcDB.getConstraintTableList();
	}
	
	public void setConstraintList(List<LHC> constraintList) {
		this.lhcDB.getConstraintTableList().setAll(constraintList);
	}
}
