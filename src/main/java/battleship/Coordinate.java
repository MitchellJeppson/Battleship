package battleship;
public class Coordinate {
	
	private int column;
	private int row;
	
	/*
	 * A coordinate specifies the head of the ship with a pair of values (column, row), 
	 * ships always conintue downward or to the right from the coordinate position.
	 */
	public Coordinate(int column, int row){
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	@Override
	public String toString(){
		return "column: "+column+"\nrow: "+row;
	}

}
