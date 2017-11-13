package battleship;

public class Board {
	private int columns;
	private int rows;
	private BattleshipObject[][] board;

	public Board(){
		this.columns = 5;
		this.rows = 5;
		board = new BattleshipObject[columns][rows];
		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				board[i][j] = new Water();
			}
		}
	}

	public Board(int columns, int rows){
		this.columns = columns;
		this.rows = rows;
		board = new BattleshipObject[columns][rows];
		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				board[i][j] = new Water();
			}
		}
	}

	public boolean addShip(Ship ship){

		if(!ship.getIsVertical()){
			if(ship.getCoordinate().getColumn() + ship.getLength() > board.length){
				System.out.println("Ship out of bounds: column "+(ship.getCoordinate().getColumn()+ship.getLength())+"/"+board.length);
				return false;
			}
			for(int i = ship.getCoordinate().getColumn(); i < ship.getCoordinate().getColumn()+ship.getLength(); i++){
				if(board[i][ship.getCoordinate().getRow()] instanceof Ship){
					System.out.println("A ship is currently already stationed here: \n\tcol: "+i+"\n\trow: "+ship.getCoordinate().getRow());
					return false;
				}
			}
			for(int i = ship.getCoordinate().getColumn(); i < ship.getCoordinate().getColumn()+ship.getLength(); i++){
				board[i][ship.getCoordinate().getRow()] = ship;
			}
			return true;
		}
		else{
			if(ship.getCoordinate().getRow() + ship.getLength() > board[0].length){
				System.out.println("Ship out of bounds : row " + (ship.getCoordinate().getRow() + ship.getLength()) + "/" + board[0].length);
				return false;
			}
			for(int i = ship.getCoordinate().getRow(); i < ship.getCoordinate().getRow()+ship.getLength(); i++){
				if(board[ship.getCoordinate().getColumn()][i] instanceof Ship){
					System.out.println("A ship is currently already stationed here: \n\tcol: "+ship.getCoordinate().getColumn()+"\n\trow: "+i);
					return false;
				}
			}
			for(int i = ship.getCoordinate().getRow(); i < ship.getCoordinate().getRow()+ship.getLength(); i++){
				board[ship.getCoordinate().getColumn()][i] = ship;
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
		if (board[column][row]  instanceof Water){
			Water tile = ((Water) board[column][row]);
			if(tile.isHit()){
				System.out.println("You have already attacked this space. Try again");
				showBoard();
				return false;
			}
			tile.setHit(true);
			showBoard();
			return true;
		}
		Ship tile = ((Ship) board[column][row]);
		int distanceFromStart;
		if(tile.getIsVertical()){
			distanceFromStart = row-tile.getCoordinate().getRow();
		}
		else{
			distanceFromStart = column-tile.getCoordinate().getColumn();
		}
		if(tile.hitList.get(distanceFromStart)){
			System.out.println("You have already attacked this space.Try again.");
			showBoard();
			return false;
		}
		tile.hitList.set(distanceFromStart, true);
		if(!tile.hitList.contains(false)){
			System.out.println("You have killed an enemy ship!");
		}
		else{
			System.out.println("You have hit an enemy ship!");
		}
		showBoard();
		return true;

	}

	public void showBoard(){

		for(int i = 0; i < board.length; i++){
			System.out.print("\t"+i+"\t");
		}

		System.out.println();
		for(int row = 0; row < board[0].length; row++){
			System.out.print(row+"|");
			for(int column = 0; column < board.length; column++){
				if(board[column][row] instanceof Water){
					if(((Water) board[column][row]).isHit()){
						System.out.print("\t*\t");
					}
					else{
						System.out.print("\t-\t");
					}
				}
				else{
					Ship tile = ((Ship) board[column][row]);
					int distanceFromStart;
					if(tile.getIsVertical()){
						distanceFromStart = row-tile.getCoordinate().getRow();
					}
					else{
						distanceFromStart = column-tile.getCoordinate().getColumn();
					}
					if(tile.hitList.get(distanceFromStart)){
						System.out.print("\tX\t");
					}
					else{
						System.out.print("\t-\t");
					}
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
				if(board[column][row] instanceof Water){
					if(((Water) board[column][row]).isHit()){
						System.out.print("\t*\t");
					}
					else{
						System.out.print("\t-\t");
					}
				}
				else{
					
					Ship tile = ((Ship) board[column][row]);
					int distanceFromStart;
					if(tile.getIsVertical()){
						distanceFromStart = row-tile.getCoordinate().getRow();
					}
					else{
						distanceFromStart = column-tile.getCoordinate().getColumn();
					}
					if(tile.hitList.get(distanceFromStart)){
						System.out.print("\tX\t");
					}
					else{
						System.out.print("\tS\t");
					}
				}

			}
			System.out.println();
		}

	}

	public boolean lost(){
		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				if(board[i][j] instanceof Ship){
					Ship tile = ((Ship) board[i][j]);
					if(tile.hitList.contains(false)){
					return false;
					}
				}
			}
		}
		return true;
	}
}


