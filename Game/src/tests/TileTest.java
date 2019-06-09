package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import application.*;

/**
 * Klasa testująca klasę Tile.
 * 
 * @author Marek Oleksik
 */
public class TileTest {
	/**
	 * Metoda testuje, czy pole posiada pionek
	 */
	@Test
	public void tileHasRedPiece() {
		Piece piece = new Piece(PieceType.RED, 1, 1, 10);
		Tile tile = new Tile(false, 1, 1, 10, 10);
		tile.setPiece(piece);
		assertTrue(tile.hasPiece() == true);
	}
	/**
	 * Metoda testuje, czy pole nie posiada pionka
	 */
	@Test
	public void tileNotHasWhitePiece() {
		Tile tile = new Tile(true, 1, 1, 10, 10);
		tile.setPiece(null);
		assertTrue(tile.hasPiece() == false);
	}
	/**
	 * Metoda testuje getter i setter pionka
	 */
	@Test
	public void getSetPieceTest() {
		Piece piece = new Piece(PieceType.RED, 1, 1, 10);
		Tile tile = new Tile(false, 1, 1, 10, 10);
		tile.setPiece(piece);

		assertTrue(tile.getPiece().equals(piece));
	}
}

