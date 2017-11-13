package battleship;

public class BattleCruiser extends Ship {
	
	BattleCruiser(){
		this.length = 4;
		for(int i = 0; i < length; i++){
			this.hitList.add(false);
		}
	}
}
