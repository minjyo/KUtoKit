  package kutokit.model.ctm;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CTMDataStore {
 int test;
	ArrayList<ObservableList<CTM>> CTMTableList = new ArrayList<>();

	public ArrayList<ObservableList<CTM>> getCTMTableList() {
		return CTMTableList;
	}

}