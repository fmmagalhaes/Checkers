import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckersClass implements Checkers {

	private int turn;
	Map<Position, Piece> table = new TreeMap<Position, Piece>(
			new PositionsComparator());

	public CheckersClass() {
		String color = Piece.WHITE;

		for (int row = 1; row <= MAX_SIDE; row++) {

			for (int column = 1; column <= MAX_SIDE; column++) {

				NormalPiece piece = null;
				Position pos = new PositionClass(row, column);

				if (row > MAX_SIDE / 2 + 1)
					color = Piece.BLACK;

				if ((row + column) % 2 == 0
						&& (row < MAX_SIDE / 2 || row > MAX_SIDE / 2 + 1))
					piece = new NormalPiece(color, pos);

				table.put(pos, piece);
			}
		}

		/*
		 * for (int row = 1; row <= MAX_SIDE; row++) {
		 * 
		 * for (int column = 1; column <= MAX_SIDE; column++) { if (column==8 ||
		 * column == 7 || column == 6) table.put(new PositionClass(row, column),
		 * null); } }EXPERIENCIA
		 */

		turn = 0;
	}

	private boolean canMove(Position orig, Position dest) {
		/*
		 * condiçoes: a posição de origem tem de estar ocupado o movimento deve
		 * ser possível segundo as regras do jogo a posição de destino deve
		 * estar vazia não pode haver peças entre as duas posições, exclusive
		 */
		return table.get(orig) != null && table.get(orig).canMove(dest)
				&& table.get(dest) == null
				&& table.get(getIntermediate(orig, dest)) == null;
	}

	private boolean canJump(Position orig, Position dest) {
		/*
		 * condiçoes: a posição de origem tem de estar ocupada; o movimento deve
		 * ser possível segundo as regras do jogo; a posição de destino deve
		 * estar vazia; deve haver apenas uma peça entre as duas posições, cuja
		 * cor é contrária à cor da peça na posição de origem;
		 */
		return table.get(orig) != null
				&& table.get(orig).canJumpOver(dest)
				&& table.get(dest) == null
				&& table.get(getIntermediate(orig, dest)) != null
				&& table.get(getIntermediate(getIntermediate(orig, dest), dest)) == null
				&& !table.get(getIntermediate(orig, dest)).getColor()
						.equals(table.get(orig).getColor());
	}

	private void move(Position orig, Position dest) {
		/*
		 * move a peça na posição de origem para a posição de destino coloca
		 * vazia a posição de origem
		 */
		table.get(orig).move(dest);
		table.put(dest, table.get(orig));
		table.put(orig, null);
	}

	// private int howManyPiecesWouldIJumpOver(Position orig, Position dest){

	private void jump(Position orig, Position dest) {
		/*
		 * move a peça na posição de origem para a posição de destino coloca
		 * vazia a posição de origem coloca vazia a posição intermédia onde se
		 * encontra a peça que será comida
		 */
		table.get(orig).move(dest);
		table.put(dest, table.get(orig));
		table.put(orig, null);
		table.put(getIntermediate(orig, dest), null);
		// dest is now orig pos for next move
		/**
		 * if(mustJumpAgain(dest)!=null) jump(dest, mustJumpAgain(dest));
		 **/
	}

	private Position getIntermediate(Position orig, Position dest) {

		/*
		 * se a posição destino estiver nas redondezas da posição origem a
		 * posição destino é considerada a posição intermédia
		 */
		if (Math.abs(dest.getRow() - orig.getRow()) == 1)
			return dest;

		int row = 0, column = 0;

		/*
		 * dependendo da localização relativa das posições, a linha e a coluna
		 * da hipotética posição intermédia são alteradas
		 */
		if (dest.getRow() > orig.getRow())
			row = orig.getRow() + 1;
		else if (dest.getRow() < orig.getRow())
			row = orig.getRow() - 1;
		if (dest.getColumn() > orig.getColumn())
			column = orig.getColumn() + 1;
		else if (dest.getColumn() < orig.getColumn())
			column = orig.getColumn() - 1;

		// se existir, a peça intermédia está antes da posição destino
		if (Math.abs(dest.getRow() - row) == 1)
			return new PositionClass(row, column);
		else {
			// foi encontrada uma peça entre as duas posições
			if (table.get(new PositionClass(row, column)) != null)
				return new PositionClass(row, column);
			/*
			 * não foi encontrada a peça intermédia. deve-se continuar a
			 * procurar
			 */
			return getIntermediate(new PositionClass(row, column), dest);
		}
	}

	@Override
	public void changeTurn() {
		turn = (turn + 1) % 2;
	}

	@Override
	public String checkTurn() {
		if (turn == 0)
			return Piece.WHITE;
		return Piece.BLACK;
	}

	@Override
	public void autoMove(Position orig) {
		Position rand_orig = orig;
		if (orig == null) {
			List<Position> autoOrig = new ArrayList<Position>();
			Iterator<Position> it = positionsIterator();
			while (it.hasNext()) {
				rand_orig = it.next();
				if (!isPieceCornered(rand_orig)
						&& table.get(rand_orig).getColor().equals(checkTurn()))
					autoOrig.add(rand_orig);
			}
			Collections.shuffle(autoOrig);
			rand_orig = ((Position) autoOrig.get(0));
			for (int i = 1; i < autoOrig.size(); i++) {
				if (!amIUnableToJump(rand_orig))
					break;
				rand_orig = autoOrig.get(i);
			}
		}

		boolean amIJumping = false;

		List<Position> autoDest = new ArrayList<Position>();
		Iterator<Position> it = positionsIterator();
		while (it.hasNext()) {
			Position rand_dest = it.next();
			if (canITakeAction(rand_orig.getRow(), rand_orig.getColumn(),
					rand_dest.getRow(), rand_dest.getColumn()))
				autoDest.add((rand_dest));
		}
		Collections.shuffle(autoDest);
		Position rand_dest = ((Position) autoDest.get(0));
		// if(checkTurn().equals("WHITE")){
		for (int i = 1; i < autoDest.size(); i++) {
			if (canJump(rand_orig, rand_dest)) {
				amIJumping = true;
				break;
			}
			rand_dest = autoDest.get(i);
		}
		// }
		takeAction(rand_orig.getRow(), rand_orig.getColumn(),
				rand_dest.getRow(), rand_dest.getColumn());

		/*
		 * continua a comer enquanto pode e caso o seu primeiro movimento nesta
		 * jogada tenha sido um jump
		 */
		while (amIJumping && !amIUnableToJump(rand_dest)) {
			autoMove(rand_dest); // cause rand_dest is now the origin position
		}

	}

	@Override
	public boolean canITakeAction(int row_orig, int column_orig, int row_dest,
			int column_dest) {
		Position orig = new PositionClass(row_orig, column_orig);
		Position dest = new PositionClass(row_dest, column_dest);

		return (canMove(orig, dest) || canJump(orig, dest))
				&& checkTurn().equals(table.get(orig).getColor());
	}

	@Override
	public void takeAction(int row_orig, int column_orig, int row_dest,
			int column_dest) {
		Position orig = new PositionClass(row_orig, column_orig);
		Position dest = new PositionClass(row_dest, column_dest);

		if (canMove(orig, dest))
			move(orig, dest);
		else
			jump(orig, dest);

		// peça passa a dama
		if ((table.get(dest).getColor().equals(Piece.WHITE) && row_dest == 8)
				|| (table.get(dest).getColor().equals(Piece.BLACK) && row_dest == 1))
			table.put(dest, new King(table.get(dest).getColor(), dest));

	}

	@Override
	public Iterator<Position> positionsIterator() {
		return table.keySet().iterator();
	}

	private Iterator<Piece> piecesIterator() {
		return table.values().iterator();
	}

	private boolean isPieceCornered(Position pos) {
		Iterator<Position> it = positionsIterator();
		while (it.hasNext()) {
			Position dest = it.next();
			// se houver uma posição para onde se possa mover, a peça NÃO está
			// encurralada
			if (canITakeAction(pos.getRow(), pos.getColumn(), dest.getRow(),
					dest.getColumn()))
				return false;
		}
		// chega aqui se nenhuma peça tiver opção de movimento
		return true;
	}

	private boolean amIUnableToJump(Position orig) {
		Iterator<Position> it = positionsIterator();
		while (it.hasNext()) {
			Position dest = it.next();
			if (canJump(orig, dest))
				return false;
		}
		return true;
	}

	@Override
	public boolean isPlayerCornered() {
		Iterator<Piece> it = piecesIterator();
		while (it.hasNext()) {
			Piece piece = it.next();
			// se há uma peça do jogador que não esteja encurralada, o jogador
			// (ainda) não perdeu
			if (piece != null && piece.getColor().equals(checkTurn())
					&& !isPieceCornered(piece.getPosition()))
				return false;
		}
		// chega aqui se o jogador não tiver peças ou estiverem todas
		// encurraladas
		return true;
	}

	/**
	 * public boolean doIHaveToJump(){ Iterator<Piece> it = piecesIterator();
	 * while(it.hasNext()){ Piece piece = it.next(); Iterator<Position> it2 =
	 * positionsIterator(); while(it2.hasNext()){ Position dest = it2.next();
	 * if(piece.getColor().equals(checkTurn()) && canJump(piece.getPosition(),
	 * dest)) return true; } } return false; }
	 **/

	@Override
	public String printLine(int row) {
		String str = row + " ";
		for (int column = 1; column <= MAX_SIDE; column++) {
			Position pos = new PositionClass(row, column);
			if (table.get(pos) != null) {
				if (table.get(pos).getColor().equals(Piece.WHITE)) {
					if (table.get(pos) instanceof NormalPiece)
						str += "w";
					else
						str += "W";
				} else {
					if (table.get(pos) instanceof NormalPiece)
						str += "b";
					else
						str += "B";
				}

			} else
				str += "_";
		}
		// str+=" "+row;
		return str;
	}
}
