package kutokit.model.pmm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "ControlAction")
public class ProcessModelValueXML {

	ProcessModel PM = new ProcessModel();
	
	@XmlElement(name = "OutputVariable")
	private String outputVariable;
	@XmlElement(name = "Valuelist")
	private ObservableList<ProcessModel> valueList = FXCollections.observableArrayList();
	
	public String getOutputVariableName() {
		return this.PM.getOutputName();
	}

	public void setOutputVariableName(String OutputVariableName) {
		outputVariable = OutputVariableName;
	}
	public ObservableList<String> getValueList() {
		return this.PM.getValuelist();
	}

	public void setValueList(ObservableList<ProcessModel> valueListName) {
		valueList = valueListName;
	}
}
