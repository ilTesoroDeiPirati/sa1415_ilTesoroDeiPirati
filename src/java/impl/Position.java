package impl;

import interfaces.IPosition;

public class Position implements IPosition {
	
	private int x, y;
	private String pos;

	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


}
