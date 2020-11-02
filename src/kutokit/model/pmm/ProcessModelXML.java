package kutokit.model.pmm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProcessModel")
public class ProcessModelXML {
	
	ProcessModel PM = new ProcessModel();
		
	@XmlElementWrapper(name = "Controller")
	private String controller;
	@XmlElement(name = "ControlAction")
	private String controlAction;
	
	public String getControllerName() {
		return this.PM.getControllerName();
	}
	
	public void setControllerName(String controllerName) {
		this.controller = controllerName;
	}
		
	public String getControlActionName() {
		return this.PM.getControlActionName();
	}

	public void setControlActionName(String controlActionName) {
		this.controlAction = controlActionName;
	}

}

