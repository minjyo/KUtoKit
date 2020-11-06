package kutokit.view.components;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelView extends Label {

//	StringProperty label;
//	ListProperty<String> CAS;
//	List<String> a;
//	
	String label;
	ArrayList<String> CA;
	ObservableList<String> listItems;
	String type;
	
	public LabelView(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, ArrayList<String> CA, String type, int[] endNum) {
		this.type = type;
		
		if(type=="CA") {
			layoutXProperty().bind(endX.add(50).add((endNum[0]-1)*200).add(10));
			layoutYProperty().bind(endY.subtract(endY.subtract(startY.add(100)).divide(2)).subtract(10));
		}else {
			layoutXProperty().bind(endX.add(150).add((endNum[1]-1)*200).add(10));
			layoutYProperty().bind(endY.add(100).subtract(endY.add(100).subtract(startY).divide(2)).subtract(10));
		}
		
		
		updateArrayCA(CA);

		setFont(new Font(18));
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
		
		this.label = label;
	}	
}
