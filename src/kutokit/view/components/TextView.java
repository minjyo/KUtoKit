package kutokit.view.components;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kutokit.model.cse.Components;

public class TextView extends Label {
	public int id;
	public String content;
	public DoubleProperty x, y;
	Components dataStore;
		
	public TextView(DoubleProperty x, DoubleProperty y, String content, int id, Components dataStore) {
	
		this.dataStore = dataStore;
		this.id = id;
		this.x = x;
		this.y = y;
		this.content = content;
		
		setLayoutX(x.get());
		setLayoutY(y.get());
		
		x.bind(layoutXProperty());
		y.bind(layoutYProperty());
		
		//setBackground(new Background(new BackgroundFill(Color.web("#8fbc8f"), null, null)));
		setText(content);
		setFont(new Font(18));
		setTextFill(Color.web("#8fbc8f"));
		
		enableDrag();
	}

	// make a node movable by dragging it around with the mouse.
			private void enableDrag() {
				final Delta dragDelta = new Delta();

				setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						// record a delta distance for the drag and drop operation.
						dragDelta.x = getLayoutX() - mouseEvent.getX();
						dragDelta.y = getLayoutY() - mouseEvent.getY();
						getScene().setCursor(Cursor.MOVE);
					}
				});
				setOnMouseReleased(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						getScene().setCursor(Cursor.HAND);
						
						dataStore.moveText(id, getLayoutX(), getLayoutY());
					}
				});
				setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						setLayoutX(mouseEvent.getX() + dragDelta.x);
						setLayoutY(mouseEvent.getY() + dragDelta.y);	
					}
				});
				setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (!mouseEvent.isPrimaryButtonDown()) {
							getScene().setCursor(Cursor.HAND);
						}
					}
				});
				setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (!mouseEvent.isPrimaryButtonDown()) {
							getScene().setCursor(Cursor.DEFAULT);		
						}
					}
				});
			}
			
			static class Delta {
				double x, y;
			}
}
