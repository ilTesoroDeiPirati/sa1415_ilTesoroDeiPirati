package impl;

import jason.asSyntax.Literal;
import java.util.ArrayList;
import main.Main;
import utils.Utils;

/**
 * @author Alessia Papini
 */

public class PlayerJason{
	
	private ArrayList<Card> cards, missingCards;
	private String name;
	private String turn;
	private Grid grid;
	private PlayerJason otherPlayer;
	private int trust, bluff;

	public PlayerJason(String name, int bluff){
		this.name = name;
		this.grid = Main.grid;
		this.bluff = bluff;
	}

	public String getName() {
		return name;
	}
	
	public int getBluff() {
		return bluff;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	public Literal getCardsLiteral(){
		String str = Utils.cardListToString(cards);
		Literal lit = Literal.parseLiteral("cardsOnHand(" + str + ")");
		return lit;
	}
	
	public void setTrust(int trust){
		this.trust = trust;
	}
	
	public int getTrust(){
		return trust;
	}
	
	public void setTurn(String turn){
		this.turn = turn;
	}
	
	public String getTurn(){
		return turn;
	}
	
	public Literal getTurnLiteral(){
		Literal lit = Literal.parseLiteral("turn(" + turn + ")");
		return lit;
	}
	
	public void setOtherPlayer(PlayerJason otherPlayer){
		this.otherPlayer = otherPlayer;
	}
	
	public PlayerJason getOtherPlayer(){
		return otherPlayer;
	}
	
	public void setMissingCards(ArrayList<Card> missingCards) {
		this.missingCards = missingCards;
	}
	
	public ArrayList<Card> getMissingCard(){
		return missingCards;
	}
	
	public Literal getMissingCardsLiteral(){
		String str = Utils.cardListToString(missingCards);
		Literal lit = Literal.parseLiteral("missingCards(" + str + ")");
		return lit;
	}

}
