import java.util.Iterator;


public interface Checkers {
	public static final int MAX_SIDE = 8;

	boolean canITakeAction(int row_orig, int column_orig, int row_dest, int column_dest);
	
	void takeAction(int row_orig, int column_orig, int row_dest, int column_dest);
	
	boolean isPlayerCornered();
	
	Iterator<Position> positionsIterator();
	
	String printLine(int row);

	String checkTurn();

	void autoMove(Position orig);

	void changeTurn();

}
