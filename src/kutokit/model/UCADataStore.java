package kutokit.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UCADataStore {

	ObservableList<UCA> UCATableList = FXCollections.observableArrayList();

	public ObservableList<UCA> getUCATableList() {
		return UCATableList;
	}

//	public void addUCA(UCA uca) {
//		this.UCATableList.add(uca);
//	}
//
//	public void removeUCA(UCA uca,int index)
//	{
//		UCATableList.remove(index);
//	}

//	public void setUCA(UCA uca,int index)
//	{
//		UCATableList.set(index, uca);
//		System.out.println(index + " : " + uca.getControlAction());
//	}

//	public void setUCATableList(ObservableList<UCA> UCATableList)
//	{
//		this.UCATableList = UCATableList;
//	}


}
