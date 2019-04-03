package model;

public abstract class Player {

	/**
	 * Determines how the game is updated. If true, the user must interact with
	 * the user interface to make a move. Otherwise, the game is updated via
	 * {@link #updateGame(Game)}.
	 * 
	 * @return true if this player represents a user.
	 */
	public abstract boolean isHuman();
	
	/**
	 * Updates the game state to take a move for the current player. If there
	 * is a move available that is multiple skips, it may be performed at once
	 * by this method or one skip at a time.
	 * 
	 * @param game	the game to update.
	 */
	public abstract void updateGame(Game game);
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[isHuman=" + isHuman() + "]";
	}
}