package kutokit.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UCADataStore {

	ObservableList<UCA> UCATableList = FXCollections.observableArrayList();

	public ObservableList<UCA> getUCATableList() {
		return UCATableList;
	}

	public void addUCA(UCA uca) {
		this.UCATableList.add(uca);
	}


}
