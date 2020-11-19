package kutokit.model.ctm;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CTMDataStore {

	public String ctmController, ctmControlAction;
	public ArrayList<String> ctmCases = new ArrayList<String>();
	public ArrayList<String[]> ctmContexts = new ArrayList<String[]>();
	public ArrayList<String> ctmHazardous = new ArrayList<String>();
	public int rowSize;
	
	ObservableList<CTM> CTMTableList = FXCollections.observableArrayList();

	public ObservableList<CTM> getCTMTableList() {
		rowSize = CTMTableList.size();
		if(rowSize > 0) {
			ctmController = this.CTMTableList.get(0).getControllerName();
			ctmControlAction = this.CTMTableList.get(0).getControlAction();
			ArrayList<String> tempCases = new ArrayList<String>();
			ArrayList<String> tempHazardous = new ArrayList<String>();
			ArrayList<String[]> tempContexts = new ArrayList<String[]>();
			for(int i=0;i<rowSize;i++) {
				tempCases.add(this.CTMTableList.get(i).getCasesValue());
				tempHazardous.add(this.CTMTableList.get(i).getHazardousValue());
				tempContexts.add(this.CTMTableList.get(i).getContexts());
			}
			ctmCases = tempCases;
			ctmHazardous = tempHazardous;
			ctmContexts = tempContexts;
			
		}
		
		if(this.CTMTableList.size()==0 && ctmCases.size()>0) {
			rowSize = ctmCases.size();
			
			ObservableList<String>hazardousOX = FXCollections.observableArrayList();
			hazardousOX.add("X");
			hazardousOX.add("O");
			
			ObservableList<String>casesCombo = FXCollections.observableArrayList();
			casesCombo.add("Providing Causes Hazard");
			casesCombo.add("Not Providing Causes Hazard");
			casesCombo.add("Incorrect Timing/Order");
			casesCombo.add("Stopped Too Soon/Applied Too Long");

			for(int i=0;i<rowSize;i++) {
	    		ComboBox<String> comboBox1 = new ComboBox<String> (casesCombo);
	    		ComboBox<String> comboBox2 = new ComboBox(hazardousOX);
				comboBox1.setValue(this.ctmCases.get(i));
				comboBox2.setValue(this.ctmHazardous.get(i));
				this.CTMTableList.add(new CTM(ctmController,ctmControlAction,comboBox1,i+1,ctmContexts.get(i),comboBox2));
				CTMTableList.get(i).setCasesValue(this.ctmCases.get(i));
				CTMTableList.get(i).setHazardousValue(this.ctmHazardous.get(i));
				final int temp = i;
	      		comboBox1.valueProperty().addListener(new ChangeListener<String>() {
	  			      @Override
	  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
	  			    	CTMTableList.get(temp).setCasesValue(newValue);
	  			    	ctmCases.set(temp, newValue);
	  			    	getCTMTableList();
	  			      }
  			    });
        		comboBox2.valueProperty().addListener(new ChangeListener<String>() {
  			      @Override
  			      public void changed(ObservableValue observable, String oldValue, String newValue) {
	  			    	CTMTableList.get(temp).setHazardousValue(newValue);
	  			    	ctmHazardous.set(temp, newValue);
	  			    	getCTMTableList();
  			      }
  			    });
			}
		}
		return this.CTMTableList;
	}
	


	public String getController() {
		return this.ctmController;
	}

	public void setController(String controller) {
		this.ctmController = controller;
	}

	public String getControlAction() {
		return this.ctmControlAction;
	}

	public void setControlAction(String controlAction) {
		this.ctmControlAction = controlAction;
	}
	
	public ArrayList<String> getCases() {
		return this.ctmCases;
	}

	public void setCases(ArrayList<String> cases) {
		this.ctmCases = cases;
	}
	
	public ArrayList<String[]> getContexts() {
		return this.ctmContexts;
	}

	public void setContexts(ArrayList<String[]> contexts) {
		this.ctmContexts = contexts;
	}
	
	public ArrayList<String> getHazardous() {
		return this.ctmHazardous;
	}

	public void setHazardous(ArrayList<String> hazardous) {
		this.ctmHazardous = hazardous;
	}
	
	
} 