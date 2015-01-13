package impl;

import interfaces.IPirate;
import interfaces.IPosition;

/**
 * @author Alessia Papini
 */

public class Pirate implements IPirate {

	
	private String player;
	private IPosition position;
	
	public Pirate(String player, IPosition position){
		this.player = player;
		this.position = position;
	}
	
	public String getPlayerName(){
		return player;
	}
	
	public void setPosition(IPosition position) {
		this.position = position;
	}

	public IPosition getPosition() {
		return position;
	}

}
