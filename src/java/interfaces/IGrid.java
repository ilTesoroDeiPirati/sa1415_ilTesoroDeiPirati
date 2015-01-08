package interfaces;

import java.util.List;


import impl.Pirate;
import impl.Position;

/**
 * Identifica la griglia di gioco
 * 
 * @author Alessia Papini
 */

public interface IGrid {
	
	/**
	 * Utilizzato per posizionare un pirata sulla
	 * griglia di gioco
	 * 
	 * @param pos identifica la casella nella quale si vuole posizionare
	 * 		  il pirata
	 * @param pirate identifica il pirata che si vuole posizionare
	 * 
	 */
	public void setPirate(Position pos, Pirate pirate);
	
	/**
	 * Controlla lo stato della griglia di gioco
	 * 
	 * @return lista dei pirati posizionati sulla griglia
	 */
	public List<IPirate> allPiratePosition();
	/**
	 * Controlla che in una specifica posizione
	 * non sia stato posizionato nessun pirata
	 * 
	 * @param box identifica la casella scelta
	 * @return false se la posizione richiesta è già occupata
	 */
	public boolean isFree(Position box);
	
	public void printPosition();
	
}
