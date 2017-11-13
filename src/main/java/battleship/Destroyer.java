package battleship;

public class Destroyer extends Ship{

	
	public Destroyer(){
		this.length = 3;
		for(int i = 0; i < length; i++){
			this.hitList.add(false);
		}
	}
}
