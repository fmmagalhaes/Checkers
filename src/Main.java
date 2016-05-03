import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static final String MOVE = "MOVE";
	public static final String MOVED = "MOVED_SUCCESSFULLY";
	public static final String NOP = "CHECK THE RULES\n";

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Checkers checkers = new CheckersClass();
		processOption(checkers, in);
		in.close();
	}

	public enum Command {
		MOVE, LIST, EXIT, R, UNKNOWN
	}

	private static void processOption(Checkers checkers, Scanner in) {
		System.out.println();
		processList(checkers);
		System.out.println();
		//Command c = getCommand(in);

		/**while (!c.equals(Command.EXIT)) {
			switch (c) {
			case MOVE:
				processMove(checkers, in);
				break;
			case LIST:
				processList(checkers);
				break;
			case R:
				processRandom(checkers);
				break;
			case UNKNOWN:
				break;
			default:
				break;
			}
			**/
			System.out.println();
			String mode = in.nextLine().trim();
			if(mode.equals("R"))
				while(!checkers.isPlayerCornered()){
				processRandom(checkers);			
				}
			else if (mode.equals("P"))
				while(!checkers.isPlayerCornered())
					processMove(checkers, in);
			else{//CPU
				while(!checkers.isPlayerCornered()){
					processMove(checkers, in);
					if(checkers.isPlayerCornered())
						break;
					try {
					    Thread.sleep(2000);               //deixa o pc pensar
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					processRandom(checkers);
				}
			}
			
				
			System.out.println("\nTHE END");
			processOption(new CheckersClass() , in);
			in.close();		
			//c = getCommand(in);
		//}

	}
	private static void processRandom(Checkers checkers) {
		checkers.autoMove(null);
		System.out.println();
		processList(checkers);
		checkers.changeTurn();
	}
	

	private static void processMove(Checkers checkers, Scanner in) {
		int row_orig = in.nextInt();
		int column_orig = in.nextInt();
		int row_dest = in.nextInt();
		int column_dest = in.nextInt();
		String mjump = in.nextLine();
		System.out.println();
		if(!checkers.canITakeAction(row_orig, column_orig, row_dest, column_dest)){
			System.out.println(NOP);
			processMove(checkers, in);
		}
		//else if(checkers.checkTurn() == checkers.whosePieceIsThis(row_orig, column_orig))
		//	System.out.println("NOT YOUR TURN");
		else{
			checkers.takeAction(row_orig, column_orig, row_dest, column_dest);
			processList(checkers);
			System.out.println();
			if(mjump.contains("+"))
				processMove(checkers, in);
			else
				checkers.changeTurn();
		}	
		
			
			
	}

	private static void processList(Checkers checkers) {
	/*	Iterator it = checkers.positionsIterator();
		Position pos = null;
		while (it.hasNext()) {
			pos = (Position) it.next();

			System.out.println("(" + pos.getRow() + ", " + pos.getColumn()
					+ ")");

			System.out.println(checkers. + "\n");
		}*/
		//System.out.println("  12345678\n");
		for (int row = Checkers.MAX_SIDE; row >= 1; row--)
			System.out.println(checkers.printLine(row));
		System.out.println("\n  12345678\n");

	}

	private static Command getCommand(Scanner in) {
		try {
			String com = in.next().toUpperCase().trim();
			return Command.valueOf(com);

		} catch (IllegalArgumentException e) {

			return Command.UNKNOWN;
		}

	}

}
