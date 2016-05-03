public class King extends AbstractPiece {

	public King(String color, Position pos) {
		super(color, pos);
	}

	public boolean canMove(Position dest) {
		return Math.abs(pos.difRow(dest)) == Math.abs((pos.difColumn(dest)))
				&& Math.abs(pos.difRow(dest)) >= 1;
	}

	@Override
	public boolean canJumpOver(Position dest) {
		return Math.abs(pos.difRow(dest)) == Math.abs((pos.difColumn(dest)))
				&& Math.abs(pos.difRow(dest)) >= 2;
	}

}
