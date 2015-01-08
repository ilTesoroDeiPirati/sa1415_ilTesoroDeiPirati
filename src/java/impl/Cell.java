package impl;

import interfaces.ICell;
import impl.Position;

public class Cell implements ICell {

	private Position position;
	private Pirate pirate;
	
	public Cell(Position position){
		this.position = position;
	}

	public Position getCoord(){
		return position;
	}
	
	public void setPirate(Pirate pirate){
		this.pirate = pirate;
	}
	
	public Pirate getPirate(){
		return pirate;
	}
	
	public boolean isFree() {
		if(pirate == null){
			return true;
		}
		return false;
	}
}
