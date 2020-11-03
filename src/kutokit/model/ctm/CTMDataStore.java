package kutokit.model.ctm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CTMDataStore {

	ObservableList<CTM> CTMTableList = FXCollections.observableArrayList();

	public ObservableList<CTM> getCTMTableList() {
		return CTMTableList;
	}

}