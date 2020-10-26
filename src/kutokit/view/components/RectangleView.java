package kutokit.view.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleView extends StackPane {

	Rectangle r;
	public int id;
	public int name;
	public DoubleProperty x, y;
	
	public RectangleView(DoubleProperty x, DoubleProperty y, String name, int id) {
	
		this.r = new Rectangle(150, 100, Color.GREEN);
		
		this.x = x;
		this.y = y;
		x.bind(r.layoutXProperty());
		y.bind(r.layoutYProperty());
		
		//Label idLabel = new Label(Integer.toString(id));
		//idLabel.setVisible(false);

		getChildren().addAll(r, new Label(name));
		
		enableDrag();
	}
	
	//set observable x, y
	public void setProperty() {
		
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
					}
				});
				setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						setLayoutX(mouseEvent.getX() + dragDelta.x);
						setLayoutY(mouseEvent.getY() + dragDelta.y);
						//int id = Integer.parseInt(((Label) getChildren().get(2)).getText());

						//dataStore.moveController(id, shape.getLayoutX(), shape.getLayoutY());
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
