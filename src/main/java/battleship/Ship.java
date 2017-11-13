package battleship;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship extends BattleshipObject{

	protected int length;
	protected boolean isVertical;
	List<Boolean> hitList = new ArrayList<Boolean>(length);	
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public boolean getIsVertical() {
		return isVertical;
	}
	public void setIsVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

}
