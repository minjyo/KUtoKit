package kutokit.model;

import javafx.beans.property.SimpleStringProperty;

public class LHC {

	private SimpleStringProperty index;
	private SimpleStringProperty text;
	private SimpleStringProperty link;
	
	/*
	 * default constructor
	 */
	public LHC() {
		this(null, null, null);
	}
	
	/*
	 * initializing constructor
	 */
	public LHC(SimpleStringProperty index, SimpleStringProperty text, SimpleStringProperty link) {
		//super();
		this.index = index;
		this.text = text;
		this.link = link;
	}

	public SimpleStringProperty getIndexProperty() {
		return index;
	}

	public void setIndex(SimpleStringProperty index) {
		this.index = index;
	}
	
	public SimpleStringProperty getTextProperty() {
		return text;
	}

	public void setText(SimpleStringProperty text) {
		this.text = text;
	}
	
	public SimpleStringProperty getLinkProperty() {
		return link;
	}

	public void setLink(SimpleStringProperty link) {
		this.link = link;
	}
}