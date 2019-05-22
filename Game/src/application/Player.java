package application;

/**
 *
 * @author Marek Oleksik
 */

public class Player {
	String redPlayer;
	String whitePlayer;
	boolean isRedTurn = true;

	public String getRedPlayer() {
		return redPlayer;
	}

	public void setRedPlayer(String redPlayer) {
		this.redPlayer = redPlayer;
	}

	public String getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public boolean isRedTurn() {
		return isRedTurn;
	}

	public void setRedTurn(boolean isRedTurn) {
		this.isRedTurn = isRedTurn;
	}

}
