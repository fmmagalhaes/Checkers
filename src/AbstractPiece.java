
public abstract class AbstractPiece implements Piece{

	protected String color;
	protected Position pos;
	
	
	protected AbstractPiece(String color, Position pos){
		this.color = color;
		this.pos = pos;
	}
	
	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Position getPosition() {
		return pos;
	}
	
	@Override
	public void move(Position dest){
		pos = dest;
	}

	@Override
	public abstract boolean canJumpOver(Position dest);
	
	public abstract boolean canMove(Position dest);
	
	

}
