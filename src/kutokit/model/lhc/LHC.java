package kutokit.model.lhc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LHC {

	private StringProperty index, text, link;
	/*
	 * default constructor
	 */
	public LHC() {
		this(null, null, null);
	}
	
	/*
	 * initializing constructor
	 */
	public LHC(String index, String text, String link) {
		this.index = new SimpleStringProperty(index);
		this.text = new SimpleStringProperty(text);
		this.link = new SimpleStringProperty(link);
	}

	public String getIndex() {
		return index.get();
	}
	
	public StringProperty indexProperty() {
		return index;
	}

	public void setIndex(String index) {
		this.index.set(index);
	}
	
	public String getText() {
		return text.get();
	}
	
	public StringProperty textProperty() {
		return text;
	}

	public void setText(String text) {
		this.text.set(text);
	}
	
	public String getLink() {
		return link.get();
	}
	
	public StringProperty linkProperty() {
		return link;
	}

	public void setLink(String link) {
		this.link.set(link);
	}

}