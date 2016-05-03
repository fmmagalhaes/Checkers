public class NormalPiece extends AbstractPiece {

	public NormalPiece(String color, Position pos) {
		super(color, pos);
	}


	public boolean canMove(Position dest) {
		int to_Black = 0;
		if (color.equals(BLACK))
			to_Black = 2;
		return pos.difRow(dest) == 1 - to_Black
				&& Math.abs((pos.difColumn(dest))) == 1;
	}

	@Override
	public boolean canJumpOver(Position dest) {
		int to_Black = 0;
		if (color.equals(BLACK))
			to_Black = 4;
		return pos.difRow(dest) == 2 - to_Black
				&& Math.abs(pos.difColumn(dest)) == 2;
	}

}
