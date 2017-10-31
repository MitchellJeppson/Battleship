package battleship;
public class Ship{
	
	private int length;
	private boolean isVertical;
	private Coordinate coordinate;

	public Ship(Coordinate coordinate){
		this.length = 3;
		this.isVertical = true;
		this.coordinate = coordinate;
	}
	
	public Ship(int length, boolean isVertical, Coordinate coordinate){
		this(coordinate);
		this.length = length;
		this.isVertical = isVertical;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}	

}
