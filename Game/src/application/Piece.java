package application;

import Controllers.GameController;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Marek Oleksik
 */
public class Piece extends StackPane {
	private PieceType type;
	private double tileSize;

	private double mouseX, mouseY;
	private double oldX, oldY;
	private boolean isKing;
	private final Color LIGHTPIECE = Color.valueOf("white");
	private final Color DARKPIECE = Color.valueOf("red");

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

		drawEllipse(type, tileSize);

		if (type.toString().equals(GameController.turn)) {
			setOnMousePressed(e -> {
				mouseX = e.getSceneX();
				mouseY = e.getSceneY();
				System.out.println(mouseX + " + " + mouseY);
			});

			setOnMouseDragged(e -> {
				relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
			});
		}
	}

	public void drawEllipse(PieceType type, double tileSize) {
		Ellipse bg = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
		bg.setFill(Color.BLACK);

		bg.setStroke(Color.BLACK);
		bg.setStrokeWidth(tileSize * 0.03);

		bg.setTranslateX((tileSize - tileSize * 0.3125 * 2) / 2);
		bg.setTranslateY((tileSize - tileSize * 0.26 * 2) / 2 + tileSize * 0.07);

		Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
		ellipse.setFill(type == PieceType.RED ? DARKPIECE : LIGHTPIECE);

		ellipse.setStroke(Color.BLACK);

		ellipse.setStrokeWidth(tileSize * 0.03);

		ellipse.setTranslateX((tileSize - tileSize * 0.3125 * 2) / 2);
		ellipse.setTranslateY((tileSize - tileSize * 0.26 * 2) / 2);

		getChildren().addAll(bg, ellipse);

	}

	public void drawKing(PieceType type, double tileSize) {
		drawEllipse(type, tileSize);
		Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
		ellipse.setFill(type == PieceType.RED ? DARKPIECE : LIGHTPIECE);

		ellipse.setStroke(Color.BLACK);

		ellipse.setStrokeWidth(tileSize * 0.03);

		ellipse.setTranslateX((tileSize - tileSize * 0.3125 * 2) / 2);
		ellipse.setTranslateY((tileSize - tileSize * 0.35 * 2) / 2);
		getChildren().add(ellipse);
	}

	public void move(int x, int y) {
		oldX = x * tileSize;
		oldY = y * tileSize;
		relocate(oldX, oldY);
	}

	public void abortMove() {
		relocate(oldX, oldY);
	}

	public boolean isKing() {
		// TODO Auto-generated method stub
		return isKing;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}

	public void makeKing() {
		isKing = true;
		drawKing(type, tileSize);
		if (type == PieceType.RED) {
			type = PieceType.KINGRED;
		} else if (type == PieceType.WHITE) {
			type = PieceType.KINGWHITE;
		}

	}
}
