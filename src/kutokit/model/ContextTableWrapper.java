package kutokit.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Contexts")
public class ContextTableWrapper {
	
	private List<CTM> Contexts;
	
	@XmlElement(name = "Context")
	public List<CTM> getContexts() {
		return Contexts;
	}
	
	public void setContextTables(List<CTM> Contexts) {
		this.Contexts = Contexts;
	}

}
