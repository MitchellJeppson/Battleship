package battleship;

public class BattleshipBoard {
	private int columns;
	private int rows;
	private int[][] board;

	public BattleshipBoard(){
		this.columns = 5;
		this.rows = 5;
		board = new int[columns][rows];
	}

	public BattleshipBoard(int columns, int rows){
		this.columns = columns;
		this.rows = rows;
		board = new int[columns][rows];
	}

	public boolean addShip(Ship ship){

		if(!ship.isVertical()){
			if(ship.getCoordinate().getColumn() + ship.getLength() > board.length){
				System.out.println("Ship out of bounds: column "+(ship.getCoordinate().getColumn()+ship.getLength())+"/"+board.length);
				return false;
			}
			for(int i = ship.getCoordinate().getColumn(); i < ship.getCoordinate().getColumn()+ship.getLength(); i++){
				if(board[i][ship.getCoordinate().getRow()] == 2 || board[i][ship.getCoordinate().getRow()] == 3){
					System.out.println("A ship is currently already stationed here: \n\tcol: "+i+"\n\trow: "+ship.getCoordinate().getRow());
					return false;
				}
			}
			for(int i = ship.getCoordinate().getColumn(); i < ship.getCoordinate().getColumn()+ship.getLength(); i++){
				board[i][ship.getCoordinate().getRow()] = 2;
			}
			return true;
		}
		else{
			if(ship.getCoordinate().getRow() + ship.getLength() > board[0].length){
				System.out.println("Ship out of bounds : row " + (ship.getCoordinate().getRow() + ship.getLength()) + "/" + board[0].length);
				return false;
			}
			for(int i = ship.getCoordinate().getRow(); i < ship.getCoordinate().getRow()+ship.getLength(); i++){
				if(board[ship.getCoordinate().getColumn()][i] == 2 || board[ship.getCoordinate().getColumn()][i] == 3){
					System.out.println("A ship is currently already stationed here: \n\tcol: "+ship.getCoordinate().getColumn()+"\n\trow: "+i);
					return false;
				}
			}
			for(int i = ship.getCoordinate().getRow(); i < ship.getCoordinate().getRow()+ship.getLength(); i++){
				board[ship.getCoordinate().getColumn()][i] = 2;
			}
			return true;
		}
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean strike(int column, int row){
		if (board[column][row]  == 0){
			System.out.println("You hit open water.");
			board[column][row] = board[column][row]+1;
			showBoard();
			return true;
		}
		else if( board[column][row] == 2){
			System.out.println("You hit a ship!!");
			board[column][row] = board[column][row]+1;
			showBoard();
			return true;
		}
		else{
			System.out.println("You have already attacked this position.");
			showBoard();
			return false;
		}
	}

	public void showBoard(){
		
		for(int i = 0; i < board.length; i++){
			System.out.print("\t"+i+"\t");
		}

		System.out.println();
		for(int row = 0; row < board[0].length; row++){
			System.out.print(row+"|");
			for(int column = 0; column < board.length; column++){
				if(board[column][row] == 1){
					System.out.print("\t*\t");
				}
				else if(board[column][row] == 3){
					System.out.print("\tX\t");
				}
				else{
					System.out.print("\t-\t");
				}

			}
			System.out.println();
		}
	}

	public void showSetupBoard(){
		for(int i = 0; i < board.length; i++){
			System.out.print("\t"+i+"\t");
		}
		System.out.println();
		for(int row = 0; row < board[0].length; row++){
			System.out.print(row+"|");
			for(int column = 0; column < board.length; column++){
				if(board[column][row] == 0){
					System.out.print("\t-\t");
				}
				else if(board[column][row] == 2){
					System.out.print("\tS\t");
				}
			}
			System.out.println();
		}

	}

	public boolean lost(){
		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				if(board[i][j] == 2){
					return false;
				}
			}
		}
		return true;
	}
}


