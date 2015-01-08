package interfaces;

/**
 * Rappresenta una pedina appartenente ad un giocatore
 * 
 * @author Alessia Papini
 *
 */
public interface IPirate {
	
	public void setPosition(IPosition pos);
	public IPosition getPosition();
	public String getPlayerName();
	
}
