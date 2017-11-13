package battleship;

public class Frigate extends Ship{
	
	public Frigate(){
		this.length = 2;
		for(int i = 0; i < length; i++){
			this.hitList.add(false);
		}
	}
}
