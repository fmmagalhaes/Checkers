public interface Piece {
	
	public static final String WHITE = "WHITE";
	public static final String BLACK = "BLACK";

	String getColor();

	Position getPosition();

	void move(Position dest);
	
	boolean canMove(Position dest);

	boolean canJumpOver(Position dest);


}
