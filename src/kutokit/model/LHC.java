package kutokit.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class LHC {
	
	private SimpleStringProperty type;
	private SimpleStringProperty text;
	private SimpleStringProperty index;
	private SimpleStringProperty link;
	
	public LHC(String type, String text, String index, String link) {
		this.type = new SimpleStringProperty(type);
		this.text = new SimpleStringProperty(text);
		this.index = new SimpleStringProperty(index);
		this.link = new SimpleStringProperty(link);
	}
	
	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public SimpleStringProperty getText() {
		return text;
	}

	public void setText(String text) {
		this.text.set(text);
	}

	public SimpleStringProperty getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index.set(index);
	}

	public SimpleStringProperty getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link.set(link);
	}
}