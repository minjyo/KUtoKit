package kutokit.model.utm;

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


	@XmlElement(name = "UCA")
	public ObservableList<UCA> getUCAList() {
		return this.UCAList;
	}

	public void setUCAList(ObservableList<UCA> UCAList) {
		this.UCAList = UCAList;
	}


}
