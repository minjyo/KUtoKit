package kutokit.view.components;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class LabelView extends Label {

//	StringProperty label;
//	ListProperty<String> CAS;
//	List<String> a;
//	
	String label;
	ArrayList<String> CA;
	ObservableList<String> listItems;
	String type;
	
	public LabelView(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, ArrayList<String> CA, String type) {
		this.type = type;
		
		if(type=="CA") {
			layoutXProperty().bind(endX.add(120).subtract(endX.add(120).subtract(startX.add(120)).divide(2)).add(10));
			layoutYProperty().bind(endY.subtract(endY.subtract(startY.add(100)).divide(2)).subtract(10));
		}else {
			layoutXProperty().bind(endX.add(30).subtract(endX.add(30).subtract(startX.add(30)).divide(2)).add(10));
			layoutYProperty().bind(endY.add(100).subtract(endY.add(100).subtract(startY).divide(2)).subtract(10));
		}
		
		
		updateArrayCA(CA);

		setText(this.label);
	}
	
	public void updateArrayCA(ArrayList<String> CA) {
		String label="";
		
		for(int i=0; i<CA.size(); i++) {
			if(i==0) {
				label = CA.get(0);
			}else {
				label = label + ", " + CA.get(i);
			}
			
		}
		
		System.out.println(label);
		
		this.label = label;
	}	
}
