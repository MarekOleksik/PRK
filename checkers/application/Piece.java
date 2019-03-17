package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Marek Oleksik
 */
public class Piece extends StackPane{
	private PieceType type;
	private double tileSize;

	private double mouseX, mouseY;
	private double oldX, oldY;

	public PieceType getType() {
		return type;
	}

	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}

	public Piece(PieceType type, int x, int y, double tileSize) {
		this.type = type;
		this.tileSize = tileSize;

		move(x, y);

		Ellipse bg = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
		bg.setFill(Color.BLACK);

		bg.setStroke(Color.BLACK);
		bg.setStrokeWidth(tileSize * 0.03);

		bg.setTranslateX((tileSize - tileSize * 0.3125 * 2) / 2);
		bg.setTranslateY((tileSize - tileSize * 0.26 * 2) / 2 + tileSize * 0.07);

		Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
		ellipse.setFill(type == PieceType.RED ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

		ellipse.setStroke(Color.BLACK);
		ellipse.setStrokeWidth(tileSize * 0.03);

		ellipse.setTranslateX((tileSize - tileSize * 0.3125 * 2) / 2);
		ellipse.setTranslateY((tileSize - tileSize * 0.26 * 2) / 2);

		getChildren().addAll(bg, ellipse);

		setOnMousePressed(e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});

		setOnMouseDragged(e -> {
			relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		});
	}

	public void move(int x, int y) {
		oldX = x * tileSize;
		oldY = y * tileSize;
		relocate(oldX, oldY);
	}

	public void abortMove() {
		relocate(oldX, oldY);
	}
}