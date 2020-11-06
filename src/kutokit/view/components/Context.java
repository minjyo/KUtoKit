package kutokit.view.components;

public class Context {
	int contextID;
	String controlAction;
	String cases;
	int  no;
	String  contexts;
	Boolean  hazardous;

	public Context()
	{

	}


	public Context(String controlAction, String cases,int no,String contexts,Boolean hazardous,int contextID) {
		this.controlAction = controlAction;
		this.cases = cases;
		this.no = no;
		this.contexts = contexts;
		this.hazardous = hazardous;
		this.contextID = contextID;
	}

	public String getControlAction() {
		return controlAction;
	}

	public void setControlAction(String controlAction) {
		this.controlAction = controlAction;
	}

	public String getCases() {
		return cases;
	}

	public void setCases(String cases) {
		this.cases = cases;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getContexts() {
		return contexts;
	}

	public void setContexts(String contexts) {
		this.contexts = contexts;
	}

	public Boolean getHazardous() {
		return hazardous;
	}

	public void setHazardous(Boolean hazardous) {
		this.hazardous = hazardous;
	}


	public int getcontextID() {
		return contextID;
	}

	public void setcontextID(int contextID) {
		this.contextID = contextID;
	}
}
