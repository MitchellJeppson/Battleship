package battleship;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class BattleshipGame {
	private BattleshipBoard board1;
	private BattleshipBoard board2;
	private BattleshipBoard[] boards = new BattleshipBoard[2];
	private Scanner in = new Scanner(System.in);

	public void startGame(){
		int ans = -1;
		while(ans < 0){
			System.out.println("Menu:\n1.\tStart Default Game.\n2.\tStart Game with Custom Settings.\n3.\tExit");
			try{
				ans = in.nextInt();
				if(ans > 3 || ans < 1){
					System.out.println("Please enter a number in the correct range.");
					ans = -1;
					continue;
				}
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please enter a valid integer number from the menu.");
				in.next();
			}
		}
		if(ans == 1){
			playDefault();
		}
		else if(ans == 2){
			playCustom();
		}
		else{
			System.exit(0);
		}
	}

	private void playDefault(){
		this.board1 = new BattleshipBoard();
		this.board2 = new BattleshipBoard();
		boards[0] = this.board1;
		boards[1] = this.board2;
		addDefaultShips();
		playGame();
	}

	private void addDefaultShips(){
		Random rand = new Random();
		int[] sizes = new int[3];
		for(int i = 0; i < 3; i++){
			sizes[i] = rand.nextInt(3)+1; 
		}

		for(int b = 0; b < 2; b++){
			for(int i = 0; i < 3; i++){
				boolean isVertical = rand.nextBoolean();
				Coordinate coo;
				if(isVertical){
					coo = new Coordinate(rand.nextInt(boards[b].getColumns()), boards[b].getRows()-sizes[i]);
				}
				else{
					coo = new Coordinate(rand.nextInt(boards[b].getColumns()-sizes[i]), rand.nextInt(boards[b].getRows()));
				}	
				if(!boards[b].addShip(new Ship(sizes[i], isVertical, coo))){
					i--;
				}
			}
			boards[b].showSetupBoard();
		}
		
		
		
	}


	private void playCustom(){
		while(true){
			try{
				System.out.println("Please enter the number of columns for your board:\n");
				int columns = in.nextInt();
				System.out.println("Please enter the number of rows for your board:\n");
				int rows = in.nextInt();
				if(rows > 0 && rows < 11 && columns > 0 && columns < 11){
					this.board1 = new BattleshipBoard(columns, rows);
					this.board2 = new BattleshipBoard(columns, rows);
					boards[0] = this.board1;
					boards[1] = this.board2;
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
		while(true){
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

		int[] shipLengths = new int[numShips];

		for(int i = 0; i < numShips; i++){
			try{
				System.out.println("Please enter the length of ship #"+(i+1)+":\n");
				shipLengths[i] = in.nextInt();
				if((shipLengths[i] > board1.getColumns() && shipLengths[i] > board1.getRows()) || shipLengths[i] < 1){
					System.out.println("Ship length must be greater than 0 and not bigger than your board.");
					i--;
				}
			}
			catch(InputMismatchException e){
				in.next();
				i--;
				System.out.println("Please enter an integer number.");
			}
		}

		int isVertical;
		boolean isVerticalBoolean;
		int column;
		int row;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < numShips; j++){
				System.out.println("Which way will ship #"+(j+1)+" (length: "+shipLengths[j]+") for player "+(i+1)+" face:\n1.\tVertical\n2.\tHorizontal");
				try{
					isVertical = in.nextInt();
					isVertical -= 1;
					if(isVertical > 1 || isVertical < 0){
						System.out.println("Please select option from menu above.");
						j--;
						continue;
					}
					isVerticalBoolean = (isVertical==1?false:true);
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
					Coordinate coo = new Coordinate(column, row);

					if(boards[i].addShip(new Ship(shipLengths[j],isVerticalBoolean, coo))){
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
			if(col < 0 || col > board1.getColumns()-1 || row < 0 || row > board1.getRows()-1){
				System.out.println("Invald column or row, please try again");
				continue;
			}
			BattleshipBoard attackBoard = (player1Turn?board2:board1);
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
