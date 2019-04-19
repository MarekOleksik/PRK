package application;

/**
*
* @author Marek Oleksik
*/
public enum PieceType {
	RED(1), WHITE(-1), KINGRED(0), KINGWHITE(0);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
