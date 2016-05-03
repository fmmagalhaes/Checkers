public class PositionClass implements Position{
	private int row, column;

	public PositionClass(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public int difRow(Position other) {
		return other.getRow() - getRow();
	}

	@Override
	public int difColumn(Position other) {
		return other.getColumn() - getColumn();
	}

	@Override
	public boolean equals(Position other) {
		return other.getRow() == row && other.getColumn() == column;
	}

}
