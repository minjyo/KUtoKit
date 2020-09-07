package kutokit.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Contexts")
public class ContextTableWrapper {
	
	private List<ContextTableDataModel> Contexts;
	
	@XmlElement(name = "Context")
	public List<ContextTableDataModel> getContexts() {
		return Contexts;
	}
	
	public void setContextTables(List<ContextTableDataModel> Contexts) {
		this.Contexts = Contexts;
	}

}
