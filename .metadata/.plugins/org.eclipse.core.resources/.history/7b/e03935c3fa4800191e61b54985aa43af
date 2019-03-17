package Controllers;

import application.Checkerboard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marek Oleksik
 */

public class FXMLCheckersBoardController {

	private double boardHeight;
	private double boardWidth;

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private VBox vBox;

	public void ready(Scene scene) {
		// Create change listener for width/height
		ChangeListener<Number> sizeChangeListener = (ObservableValue<? extends Number> observable, Number oldValue,
				final Number newValue) -> {
			setGameBoard();
		};

		// Add change listeners to scene
		scene.widthProperty().addListener(sizeChangeListener);
		scene.heightProperty().addListener(sizeChangeListener);

		setGameBoard();
	}

	public void setGameBoard() {
		boardWidth = vBox.getWidth();
		boardHeight = vBox.getHeight();

		// Generate new gameboard
		Checkerboard checkerboard = new Checkerboard(boardWidth, boardHeight);
		AnchorPane gameboard = checkerboard.build();

		// Clear previous gameboard
		anchorPane.getChildren().clear();

		// Set new gameboard configuration
		anchorPane.getChildren().addAll(gameboard);
	}
}