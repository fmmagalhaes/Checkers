
public interface Position{

	int getRow();
	
	int getColumn();
	
	int difRow(Position other);
	
	int difColumn(Position other);
	
	boolean equals(Position other);
	
}
