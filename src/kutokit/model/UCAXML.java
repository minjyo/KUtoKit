package kutokit.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kutokit.view.components.*;

@XmlRootElement(name = "UCA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class UCAXML {

	ObservableList<UCA> UCAList = FXCollections.observableArrayList();

	//ArrayList<UCA> UCAList_a = new ArrayList<UCA>();
//	ObservableList<LHC> hazardTableList = FXCollections.observableArrayList();
//	ObservableList<LHC> constraintTableList = FXCollections.observableArrayList();
//
//	public void ObserveToString() {
//		for(UCA a : this.UCAList) {
//			this.UCAList_a.add(new UCA(a.ControlAction,a.Provi)));
//		}
//	}


//	public ArrayList<UCA> getUCAList() {
//		return this.UCAList_a;
//	}

	@XmlElement(name = "UCA")
	public ObservableList<UCA> getUCAList() {
		return this.UCAList;
	}

	public void setUCAList(ObservableList<UCA> UCAList) {
		this.UCAList = UCAList;
	}


}
