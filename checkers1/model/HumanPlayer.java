package model;

public class HumanPlayer extends Player {

	@Override
	public boolean isHuman() {
		return true;
	}

	/**
	 * Performs no updates on the game. As human players can interact with the
	 * user interface to update the game.
	 */
	@Override
	public void updateGame(Game game) {}

}