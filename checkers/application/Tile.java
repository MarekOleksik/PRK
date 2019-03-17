package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Marek Oleksik
 */
public class Tile extends Rectangle {

	private Piece piece;
	private final Color LIGHTCOLOR = Color.valueOf("#feb");
	private final Color DARKCOLOR = Color.valueOf("#582");

	public boolean hasPiece() {
		return piece != null;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Tile(boolean light, int x, int y, double width, double height) {

		setWidth(width);
		setHeight(height);

		relocate(x * width, y * height);

		setFill(light ? LIGHTCOLOR : DARKCOLOR);
	}
}