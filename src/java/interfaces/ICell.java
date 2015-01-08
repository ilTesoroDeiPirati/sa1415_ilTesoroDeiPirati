package interfaces;

import impl.Pirate;
import impl.Position;

public interface ICell {

	public Position getCoord();
	public void setPirate(Pirate pirate);
	public Pirate getPirate();
	public boolean isFree();
	
}
