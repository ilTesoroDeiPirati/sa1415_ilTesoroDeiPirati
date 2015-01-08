package interfaces;

import impl.Card;

import java.util.ArrayList;

/**
 * Rappresentazione di un agente giocatore
 * 
 * @author Alessia Papini
 *
 */

public interface IPlayerAgent {
		
	/**
	 * 
	 * @return colore dei pirati del giocatore
	 */
	public String getColor();
	
	/**
	 * 
	 * @return nome del giocatore
	 */
	public String getName();
	
	/**
	 * Distribuisce al giocatore i tasselli per
	 * iniziare la partita
	 */
	public void setCards(ArrayList<Card> cards);
	
	public void setMissingCards(ArrayList<Card> cards);
	
	/**
	 * Comunicazione di inizio del turno del giocatore
	 */
	public void startTurn();
	
	/**
	 * Processo di ragionamento con il quale il giocatore
	 * sceglie la mossa da fare in base alla conoscenza 
	 * in suo possesso
	 */
	public void deliberate();
	
	/**
	 * Posizionamento del pirata sulla griglia di gioco
	 */
	public void positionPirate(ICard x, ICard y);
	
	/**
	 * Comunicazione di fine del turno del giocatore
	 */
	public void endTurn();
	
}
