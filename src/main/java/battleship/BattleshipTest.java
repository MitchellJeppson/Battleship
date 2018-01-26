package battleship;

public class BattleshipTest {
	
	public static void main(String[] args){
		BattleshipGame game = new BattleshipGame();
		
		//Register three types of ships for game.
		BattleshipFactory.instance().registerProduct("Frigate", Frigate.class); 
		BattleshipFactory.instance().registerProduct("Destroyer", Destroyer.class); 
		BattleshipFactory.instance().registerProduct("Battlecruiser", BattleCruiser.class); 
		
		game.startGame();		
	}

}
