import java.util.Comparator;


public class PositionsComparator implements Comparator<Position>{

	@Override
	public int compare(Position p1, Position p2) {
		if(p1.getRow()>p2.getRow())
			return 1;
		else if(p1.getRow()<p2.getRow())
			return -1;
		else if (p1.getColumn()>p2.getColumn())
			return 1;
		else if(p1.getColumn()<p2.getColumn())
			return -1;
		return 0;
	}
	

}
