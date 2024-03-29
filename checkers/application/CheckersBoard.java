package application;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marek Oleksik
 */
public class CheckersBoard {
	private double boardHeight;
	private double boardWidth;
	private double rectangleHeight;
	private double rectangleWidth;
	private final int NUMCOLS = 8;
	private final int NUMROWS = 8;
	private double rectangleSize;

	private AnchorPane gameboard = null;
	public static String[][] boardString = new String[8][8];
	private Tile[][] board = new Tile[NUMCOLS][NUMROWS];
	private Group tileGroup = new Group();
	private Group pieceGroup = new Group();

	public CheckersBoard() {

	}

	public CheckersBoard(double boardWidth, double boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}

	public AnchorPane build() {
		// Calculate the max width and height of the board squares using the
		// smallest board dimension
		if (boardWidth < boardHeight) {
			rectangleWidth = boardWidth / NUMCOLS;
			rectangleHeight = boardWidth / NUMROWS;
		} else {
			rectangleWidth = boardHeight / NUMCOLS;
			rectangleHeight = boardHeight / NUMROWS;
		}
		rectangleSize = rectangleWidth;
		gameboard = new AnchorPane();
		gameboard.getChildren().addAll(tileGroup, pieceGroup);
		// Create board squares
		for (int x = 0; x < NUMROWS; x++) {
			for (int y = 0; y < NUMCOLS; y++) {

				Tile tile = new Tile((x + y) % 2 == 0, x, y, rectangleWidth, rectangleHeight);
				board[x][y] = tile;

				tileGroup.getChildren().add(tile);

				Piece piece = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.RED, x, y);
				}

				if (y >= 5 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.WHITE, x, y);
				}

				if (piece != null) {
					tile.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
			}
		}
		return gameboard;
	}

	/*
	 * public AnchorPane fill() { // Calculate the max width and height of the board
	 * squares using the // smallest board dimension if (boardWidth < boardHeight) {
	 * rectangleWidth = boardWidth / NUMCOLS; rectangleHeight = boardWidth /
	 * NUMROWS; } else { rectangleWidth = boardHeight / NUMCOLS; rectangleHeight =
	 * boardHeight / NUMROWS; } rectangleSize = rectangleWidth; gameboard = new
	 * AnchorPane(); gameboard.getChildren().addAll(tileGroup, pieceGroup); //
	 * Create board squares for (int x = 0; x < NUMROWS; x++) { for (int y = 0; y <
	 * NUMCOLS; y++) {
	 * 
	 * Tile tile = new Tile((x + y) % 2 == 0, x, y, rectangleWidth,
	 * rectangleHeight); board[x][y] = tile;
	 * 
	 * tileGroup.getChildren().add(tile);
	 * 
	 * Piece piece = null;
	 * 
	 * // System.out.println(boardServer[x][y]); if (boardString[x][y] != null &&
	 * boardString[x][y].equals("RED")) { piece = makePiece(PieceType.RED, x, y); }
	 * 
	 * if (boardString[x][y] != null && boardString[x][y].equals("WHITE")) { piece =
	 * makePiece(PieceType.WHITE, x, y); }
	 * 
	 * if (piece != null) { tile.setPiece(piece);
	 * pieceGroup.getChildren().add(piece); } } } return gameboard; }
	 */

	public AnchorPane getBoard() {
		return gameboard;
	}

	public double getWidth() {
		return boardWidth;
	}

	public double getHeight() {
		return boardHeight;
	}

	public double getRectangleWidth() {
		return rectangleWidth;
	}

	public double getRectangleHeight() {
		return rectangleHeight;
	}

	public double getRectangleSize() {
		return rectangleSize;
	}

	private MoveResult tryMove(Piece piece, int newX, int newY) {

		if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0 || newX > boardWidth || newY > boardHeight) {
			return new MoveResult(MoveType.NONE);
		}

		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());

		System.out.println("x0= " + x0 + " y0= " + y0);
		System.out.println("newx = " + newX + " newy = " + newY);

		// piece is not King

		if (!piece.isKing()) {
			if (piece.getType().equals(PieceType.RED) && Math.abs(newX - x0) == 1
					&& newY - y0 == piece.getType().moveDir) {
				if (newY == 7) {
					piece.makeKing();
				}

				return new MoveResult(MoveType.NORMAL);
			} else if (piece.getType().equals(PieceType.WHITE) && Math.abs(newX - x0) == 1
					&& newY - y0 == piece.getType().moveDir) {
				if (newY == 0) {
					piece.makeKing();
				}

				return new MoveResult(MoveType.NORMAL);

			} else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

				int x1 = x0 + (newX - x0) / 2;
				int y1 = y0 + (newY - y0) / 2;
				System.out.println("x1= " + x1 + " y1= " + y1);
				System.out.println("newY - y0 = " + (newY - y0));

				if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
					if ((newY == 7) && piece.getType().equals(PieceType.RED)
							|| (newY == 0) && piece.getType().equals(PieceType.WHITE)) {
						piece.makeKing();
					}
					return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
				}
			}

		}

		// piece is King

		else if (piece.isKing()) {
			if ((piece.getType().equals(PieceType.KINGRED) && Math.abs(newX - x0) == 1)
					|| (piece.getType().equals(PieceType.KINGWHITE) && Math.abs(newX - x0) == 1)) {
				return new MoveResult(MoveType.NORMAL);
			} else if (Math.abs(newX - x0) == 2) {
				int x1 = x0 + (newX - x0) / 2;
				int y1 = y0 + (newY - y0) / 2;
				if (board[x1][y1].hasPiece() && (((board[x1][y1].getPiece().getType().equals(PieceType.KINGRED)
						|| board[x1][y1].getPiece().getType().equals(PieceType.RED))
						&& (piece.getType().equals(PieceType.KINGWHITE) || piece.getType().equals(PieceType.WHITE)))
						|| ((board[x1][y1].getPiece().getType().equals(PieceType.KINGWHITE)
								|| board[x1][y1].getPiece().getType().equals(PieceType.WHITE))
								&& (piece.getType().equals(PieceType.KINGRED)
										|| piece.getType().equals(PieceType.RED))))) {
					return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
				}
			} else {
				return new MoveResult(MoveType.NONE);
			}
		}

		return new MoveResult(MoveType.NONE);
	}

	private int toBoard(double pixel) {
		return (int) ((pixel + rectangleSize / 2) / rectangleSize);
	}

	private Piece makePiece(PieceType type, int x, int y) {
		Piece piece = new Piece(type, x, y, rectangleSize);

		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());

			MoveResult result;

			if (newX < 0 || newY < 0 || newX >= boardWidth || newY >= boardHeight) {
				result = new MoveResult(MoveType.NONE);
			} else {
				result = tryMove(piece, newX, newY);
			}

			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());
			System.out.println(result.getType());
			switch (result.getType()) {
			case NONE:
				piece.abortMove();
				break;
			case NORMAL:
				piece.move(newX, newY);
				board[x0][y0].setPiece(null);
				board[newX][newY].setPiece(piece);

				// setBoardServer();
				// GameController.outputPrintWriter.println("BRD" +
				// GameController.convertBoardStringToString());
				break;
			case KILL:
				piece.move(newX, newY);
				board[x0][y0].setPiece(null);
				board[newX][newY].setPiece(piece);

				Piece otherPiece = result.getPiece();
				board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				pieceGroup.getChildren().remove(otherPiece);
				// setBoardServer();
				// GameController.outputPrintWriter.println("BRD" +
				// GameController.convertBoardStringToString());
				break;
			}
		});

		return piece;
	}

	public void setBoardServer() {
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				if (board[row][col].getPiece() != null) {
					boardString[row][col] = String.valueOf(board[row][col].getPiece().getType());
				} else {
					boardString[row][col] = "";

				}
				// System.out.println(boardString[row][col]);
			}
		}
	}

}