package battleship;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

//TODO: Fix random ship placement on defaultGame
public class BattleshipGame {
	private Board playerOneBoard;
	private Board playerTwoBoard;
	private Board[] boards;
	private Scanner in = new Scanner(System.in);
	private static BattleshipFactory shipFactory = BattleshipFactory.instance();
	
	public void startGame(){
		int ans;
		while(true){ //Loop until type of game is picked
			System.out.println("Menu:\n1.\tStart Default Game.\n2.\tStart Game with Custom Settings.\n3.\tExit");
			try{
				ans = in.nextInt();
				if(ans > 3 || ans < 1){
					System.out.println("Please enter a number in the correct range.");
					continue;
				}
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please enter a valid integer number from the menu.");
				in.next();
			}
		}
		if(ans == 1) playDefault();
		else if(ans == 2) playCustom();
		else System.exit(0);
	}
	
	/*
	 * Creates two boards and fills them with default ships, then starts game.
	 */
	private void playDefault(){
		this.playerOneBoard = new Board();
		this.playerTwoBoard = new Board();
		this.boards = new Board[] {this.playerOneBoard, this.playerTwoBoard};
		addDefaultShips();
		playGame();
	}

	private void addDefaultShips(){
		Random rand = new Random();
		int[] sizes = new int[3];
		String[] types = new String[3];
		for(int i = 0; i < 3; i++){ //genereate 3 random ships.
			sizes[i] = rand.nextInt(3)+1;
			types[i] = ((sizes[i] == 1?"Frigate":(sizes[i] == 2?"Destroyer":"Battlecruiser")));
		}

		for(Board currBoard: boards) {
			for(int shipSizeIndex = 0; shipSizeIndex < 3; shipSizeIndex++){ //loop over 3 ships.
				boolean isVertical = rand.nextBoolean();
				Coordinate coo;
				
				//Set coordinates randomly
				//Coordinates are given as value pair for head of ship. 
				if(isVertical){
					coo = new Coordinate(rand.nextInt(currBoard.getColumns()), rand.nextInt(currBoard.getRows()-sizes[shipSizeIndex]));
				}
				else{
					coo = new Coordinate(rand.nextInt(currBoard.getColumns()-sizes[shipSizeIndex]), rand.nextInt(currBoard.getRows()));
				}
				
				Ship ship = (Ship) shipFactory.createProduct(types[shipSizeIndex]);
				ship.setIsVertical(isVertical);
				ship.setCoordinate(coo);
				
				//check if board can add the ship at that coordinate.
				//If not repeat loop with same ship.
				if(!currBoard.addShip(ship)){
					shipSizeIndex--;
				}
			}
			currBoard.showSetupBoard();
			System.out.println("Your final board is shown above, press <Enter> to hide your board from opposing player.\n\n");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Add space to hide the players board when game starts.
		for(int i = 0; i < 15; i++) {
			System.out.println("");
		}
	}

	private void playCustom(){
		while(true){ //Get Board rows and columns.
			try{
				System.out.println("Please enter the number of columns for your board:\n");
				int columns = in.nextInt();
				System.out.println("Please enter the number of rows for your board:\n");
				int rows = in.nextInt();
				if(rows > 0 && rows < 11 && columns > 0 && columns < 11){  //limit board size to 10x10 max and 1x1 min.
					this.playerOneBoard = new Board(columns, rows);
					this.playerTwoBoard = new Board(columns, rows);
					boards[0] = this.playerOneBoard;
					boards[1] = this.playerTwoBoard;
					break;
				}
				else{
					System.out.println("Please enter a number between 1-10 for column and row.");
				}
			}
			catch(InputMismatchException e){
				in.next();
				System.out.println("Please enter an integer number.");
			}
		}
		int numShips;
		while(true){ //Get number of ships for each player.
			try{
				System.out.println("Please enter the number of ships for each player:\n");
				numShips = in.nextInt();
				if(numShips > 0 && numShips < 6){
					break;
				}
				else{
					System.out.println("Please enter a number between 1-10.");
				}
			}
			catch(InputMismatchException e){
				in.next();
				System.out.println("Please enter an integer number.");
			}
		}
		Ship[] ships1 = new Ship[numShips];
		Ship[] ships2 = new Ship[numShips];
		for(int i = 0; i < numShips; i++){ // Get types of ships to use.
			try{
				System.out.println("Please enter the type of ship #"+(i+1)+":");
				System.out.println("1.\tFrigate(2 spaces)\n2.\tDestroyer(3 spaces)\n3.\tBattle Cruiser(4 spaces)");
				int shipType = in.nextInt();
				if(shipType  < 0 || shipType > 3){
					System.out.println("Ship Must be from selection above.");
					i--;
					continue;
				}
				else{
					String shipTypeString = ((shipType == 1?"Frigate":(shipType == 2?"Destroyer":"Battlecruiser")));
					ships1[i] = (Ship) shipFactory.createProduct(shipTypeString);
					ships2[i] = (Ship) shipFactory.createProduct(shipTypeString);
				}
			}
			catch(InputMismatchException e){
				in.next();
				i--;
				System.out.println("Please enter an integer number.");
			}
		}
		Ship[][] ships = {ships1, ships2};

		int isVertical;
		int column;
		int row;
		for(int i = 0; i < 2; i++){ //Place ships for both players
			for(int j = 0; j < numShips; j++){
				System.out.println("Which way will ship #"+(j+1)+" (length: "+ships1[j].length+") for player "+(i+1)+" face:\n1.\tVertical\n2.\tHorizontal");
				try{
					isVertical = in.nextInt();
					isVertical -= 1;
					if(isVertical > 1 || isVertical < 0){
						System.out.println("Please select option from menu above.");
						j--;
						continue;
					}
					ships[i][j].setIsVertical(isVertical==1?false:true);
					System.out.println("Which column will this ship start in:");
					column = in.nextInt();
					if(column < 0 || column > boards[i].getColumns()-1){
						System.out.println("Out of range.");
						j--;
						continue;
					}
					System.out.println("Which row will this ship start in: ");
					row = in.nextInt();
					if(row < 0 || row > boards[i].getColumns()-1){
						System.out.println("Out of range.");
						j--;
						continue;
					}
					ships[i][j].setCoordinate(new Coordinate(column, row));
					if(boards[i].addShip(ships[i][j])){
						System.out.println("Ship created successfully.");
						boards[i].showSetupBoard();
					}
					else{
						j--;
						System.out.println("Invald Ship");
						continue;
					}
				}
				catch(InputMismatchException e){
					System.out.println("Invalid input, please enter integer from menu.");
					in.next();
					j--;
				}
			}
			System.out.println("Your final board is shown above, press <Enter> to hide your board from opposing player.\n\n");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Create space to hide setup boards.
			for(int t = 0; t < 100; t++){
				System.out.println("/\n\\");
			}


		}

		playGame();
	}

	private void playGame(){
		boolean player1Turn = true;
		while(true){
			System.out.println("Player "+(player1Turn?"1":"2")+" enter the column and row to attack:\ncolumn:");
			int col = in.nextInt();
			System.out.println("row:");
			int row = in.nextInt();
			if(col < 0 || col > playerOneBoard.getColumns()-1 || row < 0 || row > playerOneBoard.getRows()-1){
				System.out.println("Invald column or row, please try again");
				continue;
			}
			Board attackBoard = (player1Turn?playerTwoBoard:playerOneBoard);
			if(!attackBoard.strike(col, row)){
				continue;
			}
			else{
				player1Turn = !player1Turn;
			}
			if(attackBoard.lost()){
				System.out.println("Player "+(player1Turn?"2":"1")+" won the game!!!");
				break;

			}
		}


	}
}
